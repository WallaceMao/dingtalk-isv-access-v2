package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.api.model.vo.corp.*;
import com.rishiqing.dingtalk.api.service.rsq.RsqAccountBizService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestHelper;
import com.rishiqing.dingtalk.svc.service.util.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:48
 */
public class StaffService {
    private static final Logger LOG= LoggerFactory.getLogger(StaffService.class);
    private static long DEFAULT_PAGE_SIZE = 50;
    @Autowired
    private CorpRequestHelper corpRequestHelper;
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private CorpDepartmentManager corpDepartmentManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    @Autowired(required = false)
    private QueueService queueService;

    /**
     * 根据userIdList中的id，从钉钉处获取用户，然后保存到本地
     *
     * @param corpId
     * @param userIdList
     * @param scopeVersion
     */
    public void fetchAndSaveCorpStaffList(String corpId, List<String> userIdList, Long scopeVersion) {
        if (userIdList == null) {
            return;
        }
        for (String userId : userIdList) {
            this.fetchAndSaveCorpStaff(corpId, userId, scopeVersion);
        }
    }

    /**
     * 1.  首先从数据库中读取corpId下的所有部门
     * 2.  循环保存部门下的所有人
     *
     * @param corpId
     */
    public Long fetchAndSaveAllCorpDepartmentStaff(String corpId, Long scopeVersion) {
        List<CorpDepartmentVO> deptList = corpDepartmentManager.getCorpDepartmentListByCorpIdAndScopeVersion(
                corpId, scopeVersion);
        long count = 0L;
        for (CorpDepartmentVO dept : deptList) {
            long userCountSaved = this.fetchAndSaveCorpDepartmentStaff(corpId, dept.getDeptId(), scopeVersion);
            count += userCountSaved;
        }
        return count;
    }

    /**
     * 保存deptId的部门，以及其所有的子部门的员工
     *
     * @param corpId
     * @param deptId
     * @param scopeVersion
     * @return
     */
    public void fetchAndSaveCorpDepartmentStaffRecursive(String corpId, Long deptId, Long scopeVersion) {
        //  先保存根部门的成员
        this.fetchAndSaveCorpDepartmentStaff(corpId, deptId, scopeVersion);
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        List<CorpDepartmentVO> deptList = corpRequestHelper.getChildCorpDepartment(
                corpTokenVO.getCorpToken(), corpId, deptId);

        //  如果无子部门，那么就返回
        if (deptList == null || deptList.size() == 0) {
            return;
        }

        //  递归保存子部门
        for (CorpDepartmentVO dept : deptList) {
            this.fetchAndSaveCorpDepartmentStaffRecursive(corpId, dept.getDeptId(), scopeVersion);
        }
    }

    /**
     * 处理deptId部门及其子部门下的所有用户，如果：
     * 1  用户还在可见范围之内，那么保留用户，更新用户所在的部门
     * 2  用户不在可见范围之内，那么删除用户。
     * 以上两种操作完成之后都需要同步到日事清中
     *
     * @param corpId
     * @param deptId
     * @param scopeVersion
     */
    public void deleteCorpDepartmentStaffNotInScopeRecursive(String corpId, Long deptId, Long scopeVersion) {
        List<CorpStaffVO> staffList = corpStaffManager.listCorpStaffByCorpIdAndDepartment(corpId, deptId);
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        for (CorpStaffVO staffVO : staffList) {
            CorpStaffVO isvStaff = corpRequestHelper.getCorpStaff(
                    corpTokenVO.getCorpToken(), corpId, staffVO.getUserId());
            // 如果用户不在可见范围内，那么会获取失败，那么得到的isvStaff除了corpId，其余均为null
            if (isvStaff.getUserId() == null) {
                // 说明不在可见范围内，那么走delete
                // 首先推送到日事清删除
                rsqAccountBizService.removeRsqTeamStaff(staffVO);
                // 然后删除本地
                corpStaffManager.deleteCorpStaffByCorpIdAndUserId(corpId, staffVO.getUserId());
            } else {
                // 说明在可见范围内，那么走update
                // 首先更新本地
                isvStaff.setScopeVersion(scopeVersion);
                corpStaffManager.saveOrUpdateCorpStaff(isvStaff);
                // 推送到日事清
                rsqAccountBizService.updateRsqTeamStaff(isvStaff);
            }
        }
        List<CorpDepartmentVO> deptList = corpDepartmentManager.getCorpDepartmentListByCorpIdAndParentId(corpId, deptId);
        if (deptList != null && deptList.size() > 0) {
            for (CorpDepartmentVO dept : deptList) {
                deleteCorpDepartmentStaffNotInScopeRecursive(corpId, dept.getDeptId(), scopeVersion);
            }
        }
    }

    /**
     * 根据authCode，去后台取，然后在本地查找
     *
     * @param authCode
     * @return
     */
    public CorpStaffVO fetchCorpStaffByAuthCode(String corpId, String authCode) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        CorpStaffVO staff = corpRequestHelper.getCorpStaffByAuthCode(
                corpTokenVO.getCorpToken(), corpId, authCode);
        return corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, staff.getUserId());
    }

    /**
     * 保存用户，并在团队用户数上加1
     *
     * @param staff
     * @param scopeVersion
     */
    public void saveCorpStaffAndAddCount(CorpStaffVO staff, Long scopeVersion) {
        String corpId = staff.getCorpId();
        staff.setScopeVersion(scopeVersion);
        CorpStaffVO dbStaff = corpStaffManager.getCorpStaffByCorpIdAndUserId(staff.getCorpId(), staff.getUserId());
        if (dbStaff == null) {
            //  如果为null，说明是新增，那么每次新增的时候需要给团队人数加1
            CorpStatisticVO corpStatisticVO = corpManager.getCorpStatisticByCorpId(corpId);
            if (corpStatisticVO == null) {
                corpStatisticVO = new CorpStatisticVO();
                corpStatisticVO.setCorpId(corpId);
                corpStatisticVO.setStaffCount(0L);
            }
            corpStatisticVO.setStaffCount(corpStatisticVO.getStaffCount() + 1);
            corpManager.saveOrUpdateCorpStatistic(corpStatisticVO);
        }
        corpStaffManager.saveOrUpdateCorpStaff(staff);

        //  然后推送到日事清
        rsqAccountBizService.createRsqTeamStaff(staff);

        //  生成解决方法
        queueService.sendToGenerateStaffSolution(corpId, staff.getUserId());
    }

    public void deleteUserAndSubtractCount(String corpId, String userId) {
        //  如果为null，说明是新增，那么每次新增的时候需要给团队人数加1
        CorpStatisticVO corpStatisticVO = corpManager.getCorpStatisticByCorpId(corpId);
        corpStatisticVO.setStaffCount(corpStatisticVO.getStaffCount() - 1);
        corpManager.saveOrUpdateCorpStatistic(corpStatisticVO);

        CorpStaffVO corpStaffVO = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);
        corpStaffManager.deleteCorpStaffByCorpIdAndUserId(corpId, userId);
        //  推送到日事清
        rsqAccountBizService.removeRsqTeamStaff(corpStaffVO);
    }

    public void updateAndPushCorpStaff(CorpStaffVO corpStaffVO, Long scopeVersion) {
        corpStaffVO.setScopeVersion(scopeVersion);
        corpStaffManager.saveOrUpdateCorpStaff(corpStaffVO);
        CorpStaffVO staffVOSaved = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpStaffVO.getCorpId(), corpStaffVO.getUserId());

        //  然后推送到日事清
        rsqAccountBizService.updateRsqTeamStaff(staffVOSaved);
    }

    private void fetchAndSaveCorpStaff(String corpId, String userId, Long scopeVersion) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        CorpStaffVO staff = corpRequestHelper.getCorpStaff(
                corpTokenVO.getCorpToken(), corpId, userId);
        staff.setScopeVersion(scopeVersion);
        corpStaffManager.saveOrUpdateCorpStaff(staff);
    }

    /**
     * 获取公司指定部门下的用户，并保存到数据库。
     * 注意，在钉钉中，部门获取的规则是这样的：
     * 假设dept-A是dept-B的父部门，dept-B中含有员工staff-1，那么在获取dept-A部门的员工时，是获取不到staff-1的
     *
     * @param corpId
     * @param deptId
     * @param scopeVersion
     * @return 返回保存的用户的个数
     */
    public Long fetchAndSaveCorpDepartmentStaff(String corpId, Long deptId, Long scopeVersion) {
        boolean hasMore = true;
        long offset = 0;
        long count = 0L;
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        while (hasMore) {
            Map<String, Object> staffPageList = corpRequestHelper.getCorpDepartmentStaffByPage(
                    corpTokenVO.getCorpToken(), corpId, deptId, offset, DEFAULT_PAGE_SIZE
            );
            //  保存用户
            List<CorpStaffVO> staffList = (List<CorpStaffVO>) staffPageList.get("list");
            count += staffList.size();
            for (CorpStaffVO corpStaff : staffList) {
                //  保存用户的时候要注意，orderInDepts和isLeaderInDepts这两个属性，这里得到的CorpStaffVO中的值只是在这个部门中的顺序号和是否是leader，
                //  如果之前就存在这个属性，那么要做合并，而不能直接覆盖
                CorpStaffVO dbStaff = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, corpStaff.getUserId());
                if (dbStaff != null) {
                    //  如果存在，需要将orderInDepts和isLeaderInDepts这两个属性合并
                    this.mergeDepartmentProperties(dbStaff, corpStaff);

                }
                corpStaff.setScopeVersion(scopeVersion);
                corpStaffManager.saveOrUpdateCorpStaff(corpStaff);
            }

            offset += DEFAULT_PAGE_SIZE;
            hasMore = (Boolean) staffPageList.get("hasMore");
        }
        return count;
    }

    private void mergeDepartmentProperties(CorpStaffVO dbStaff, CorpStaffVO targetStaff) {
        Map<Long, Long> dbOrderInDept = dbStaff.getOrderInDepts();
        if (dbOrderInDept != null) {
            Map<Long, Long> newOrderInDept = targetStaff.getOrderInDepts();
            if (newOrderInDept == null) {
                newOrderInDept = new HashMap<>(dbOrderInDept.size());
                targetStaff.setOrderInDepts(newOrderInDept);
            }
            newOrderInDept.putAll(dbOrderInDept);
        }

        Map<Long, Boolean> dbIsLeaderInDept = dbStaff.getIsLeaderInDepts();
        if (dbIsLeaderInDept != null) {
            Map<Long, Boolean> newIsLeaderInDept = targetStaff.getIsLeaderInDepts();
            if (newIsLeaderInDept == null) {
                newIsLeaderInDept = new HashMap<>(dbIsLeaderInDept.size());
                targetStaff.setIsLeaderInDepts(newIsLeaderInDept);
            }
            newIsLeaderInDept.putAll(dbIsLeaderInDept);
        }
    }

    /*
    -------------------------------------------------分割线--------------------------------------------------------------
    以下新增功能：人数限制
     */

    /**
     * 获取公司指定部门下的用户数量，不保存到数据库。
     * 注意，在钉钉中，部门获取的规则是这样的：
     * 假设dept-A是dept-B的父部门
     * 1.dept-B中含有员工staff-1，那么在获取dept-A部门的员工时，是获取不到staff-1的
     * 2.企业授权套件时：授权dept-A，那么dept-B的员工也是可以使用的，真是爆炸！
     *
     * @param corpId
     * @param deptId
     * @return 返回指定部门内的所有用户（不含子部门）
     */
    private List<CorpStaffVO> listGetDepartmentStaff(String corpId, Long deptId) {
        boolean hasMore = true;
        long offset = 0;
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        List<CorpStaffVO> staffList = new ArrayList<>();
        while (hasMore) {
            Map<String, Object> staffPageList = corpRequestHelper.getCorpDepartmentStaffByPage(
                    corpTokenVO.getCorpToken(), corpId, deptId, offset, DEFAULT_PAGE_SIZE
            );
            //add
            staffList.addAll((List<CorpStaffVO>) staffPageList.get("list"));
            offset += DEFAULT_PAGE_SIZE;
            hasMore = (Boolean) staffPageList.get("hasMore");
        }
        return staffList;
    }

    /**
     * 获取deptId的部门员工，以及其所有的子部门的员工
     *
     * @param corpId
     * @param deptId
     * @return
     */
    public void listGetCorpDepartmentStaffRecursive(String corpId, Long deptId, List<CorpStaffVO> corpStaffVOList) {
        //  获取当前部门员工
        corpStaffVOList.addAll(this.listGetDepartmentStaff(corpId, deptId));
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        List<CorpDepartmentVO> deptList = corpRequestHelper.getChildCorpDepartment(
                corpTokenVO.getCorpToken(), corpId, deptId);
        //  如果无子部门，那么就返回
        if (deptList == null || deptList.size() == 0) {
            return;
        }
        //  递归获取子部门员工
        for (CorpDepartmentVO dept : deptList) {
            this.listGetCorpDepartmentStaffRecursive(corpId, dept.getDeptId(), corpStaffVOList);
        }
    }

    /**
     * 去重统计企业总授权人数
     *
     * @param authedUserIdList 企业授权的员工userId列表
     * @param authedDeptIdList 企业授权的部门id列表
     * @return
     */
    public Long getCorpStaffCountByCorpAuthScopeInfo(List<String> authedUserIdList,List<Long> authedDeptIdList, String corpId) {
        //根据corpId获取token
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        //根据员工id查询list，防止和下面部门内的员工重复，最终一并去重
        List<CorpStaffVO> corpStaffVOList = new ArrayList<>();
        for (String userId : authedUserIdList) {
            //不需要调用http接口，浪费性能，除非以下去重规则有变化！
            //corpStaffVOList.add(corpRequestHelper.getCorpStaff(corpTokenVO.getCorpToken(), corpId, userId));
            CorpStaffVO corpStaffVO = new CorpStaffVO();
            corpStaffVO.setUserId(userId);
            corpStaffVOList.add(corpStaffVO);
        }
        for (Long authedDeptId : authedDeptIdList) {
            //递归部门查询部门以及子部门员工
            listGetCorpDepartmentStaffRecursive(corpId, authedDeptId, corpStaffVOList);
        }
        //企业授权的部门内的员工去重
        Set<CorpStaffVO> corpStaffVOSet = new TreeSet<>(new Comparator<CorpStaffVO>() {
            @Override
            public int compare(CorpStaffVO o1, CorpStaffVO o2) {
                return o1.getUserId().equals(o2.getUserId()) ? 0 : -1;
            }
        });
        corpStaffVOSet.addAll(corpStaffVOList);
        corpStaffVOList.clear();
        corpStaffVOList.addAll(corpStaffVOSet);
        LOG.debug(corpStaffVOList.toString());
        //统计
        return (long) corpStaffVOList.size();
    }


}
