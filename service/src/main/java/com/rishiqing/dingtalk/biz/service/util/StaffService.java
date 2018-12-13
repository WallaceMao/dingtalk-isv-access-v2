package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.http.CorpRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStatisticVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:48
 */
public class StaffService {
    private static long DEFAULT_PAGE_SIZE = 50;
    @Autowired
    private CorpRequestHelper corpRequestHelper;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    @Autowired
    private QueueService queueService;

    /**
     * 根据userIdList中的id，从钉钉处获取用户，然后保存到本地
     * @param corpId
     * @param userIdList
     * @param scopeVersion
     */
    public void fetchAndSaveCorpStaffList(String corpId, List<String> userIdList, Long scopeVersion){
        if(userIdList == null){
            return;
        }
        for(String userId : userIdList){
            this.fetchAndSaveCorpStaff(corpId, userId, scopeVersion);
        }
    }

    /**
     * 1.  首先从数据库中读取corpId下的所有部门
     * 2.  循环保存部门下的所有人
     * @param corpId
     */
    public Long fetchAndSaveAllCorpDepartmentStaff(String corpId, Long scopeVersion){
        List<CorpDepartmentVO> deptList = corpDepartmentManageService.getCorpDepartmentListByCorpIdAndScopeVersion(corpId, scopeVersion);
        long count = 0L;
        for(CorpDepartmentVO dept : deptList){
            long userCountSaved = this.fetchAndSaveCorpDepartmentStaff(corpId, dept.getDeptId(), scopeVersion);
            count += userCountSaved;
        }
        return count;
    }

    /**
     * 根据authCode，去后台取，然后在本地查找
     * @param authCode
     * @return
     */
    public CorpStaffVO fetchCorpStaffByAuthCode(String corpId, String authCode){
        CorpStaffVO staff = corpRequestHelper.getCorpStaffByAuthCode(corpId, authCode);
        return corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, staff.getUserId());
    }

    /**
     * 保存用户，并在团队用户数上加1
     * @param staff
     * @param scopeVersion
     */
    public void saveCorpStaffAndAddCount(CorpStaffVO staff, Long scopeVersion) {
        String corpId = staff.getCorpId();
        staff.setScopeVersion(scopeVersion);
        CorpStaffVO dbStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(staff.getCorpId(), staff.getUserId());
        if (dbStaff == null) {
            //  如果为null，说明是新增，那么每次新增的时候需要给团队人数加1
            CorpStatisticVO corpStatisticVO = corpManageService.getCorpStatisticByCorpId(corpId);
            if (corpStatisticVO == null) {
                corpStatisticVO = new CorpStatisticVO();
                corpStatisticVO.setCorpId(corpId);
                corpStatisticVO.setStaffCount(0L);
            }
            corpStatisticVO.setStaffCount(corpStatisticVO.getStaffCount() + 1);
            corpManageService.saveOrUpdateCorpStatistic(corpStatisticVO);
        }
        corpStaffManageService.saveOrUpdateCorpStaff(staff);

        //  然后推送到日事清
        rsqAccountBizService.createRsqTeamStaff(staff);

        //  生成解决方法
        queueService.sendToGenerateStaffSolution(corpId, staff.getUserId());
    }

    public void deleteUserAndSubtractCount(String corpId, String userId) {
        //  如果为null，说明是新增，那么每次新增的时候需要给团队人数加1
        CorpStatisticVO corpStatisticVO = corpManageService.getCorpStatisticByCorpId(corpId);
        corpStatisticVO.setStaffCount(corpStatisticVO.getStaffCount() - 1);
        corpManageService.saveOrUpdateCorpStatistic(corpStatisticVO);

        CorpStaffVO corpStaffVO = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        corpStaffManageService.deleteCorpStaffByCorpIdAndUserId(corpId, userId);
        //  推送到日事清
        rsqAccountBizService.removeRsqTeamStaff(corpStaffVO);
    }

    public void updateAndPushCorpStaff(CorpStaffVO corpStaffVO, Long scopeVersion) {
        corpStaffVO.setScopeVersion(scopeVersion);
        corpStaffManageService.saveOrUpdateCorpStaff(corpStaffVO);
        CorpStaffVO staffVOSaved = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpStaffVO.getCorpId(), corpStaffVO.getUserId());

        //  然后推送到日事清
        rsqAccountBizService.updateRsqTeamStaff(staffVOSaved);
    }

    private void fetchAndSaveCorpStaff(String corpId, String userId, Long scopeVersion){
        CorpStaffVO staff = corpRequestHelper.getCorpStaff(corpId, userId);
        staff.setScopeVersion(scopeVersion);
        corpStaffManageService.saveOrUpdateCorpStaff(staff);
    }

    /**
     * 获取公司指定部门下的用户，并保存到数据库
     * @param corpId
     * @param deptId
     * @param scopeVersion
     * @return 返回保存的用户的个数
     */
    private Long fetchAndSaveCorpDepartmentStaff(String corpId, Long deptId, Long scopeVersion){
        boolean hasMore = true;
        long offset = 0;
        long count = 0L;
        while(hasMore){
            Map<String, Object> staffPageList = corpRequestHelper.getCorpDepartmentStaffByPage(
                    corpId, deptId, offset, DEFAULT_PAGE_SIZE
            );
            //  保存用户
            List<CorpStaffVO> staffList = (List<CorpStaffVO>)staffPageList.get("list");
            count += staffList.size();
            for(CorpStaffVO corpStaff : staffList){
                //  保存用户的时候要注意，orderInDepts和isLeaderInDepts这两个属性，这里得到的CorpStaffVO中的值只是在这个部门中的顺序号和是否是leader，
                //  如果之前就存在这个属性，那么要做合并，而不能直接覆盖
                CorpStaffVO dbStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, corpStaff.getUserId());
                if(dbStaff != null){
                    //  如果存在，需要将orderInDepts和isLeaderInDepts这两个属性合并
                    this.mergeDepartmentProperties(dbStaff, corpStaff);

                }
                corpStaff.setScopeVersion(scopeVersion);
                corpStaffManageService.saveOrUpdateCorpStaff(corpStaff);
            }

            offset += DEFAULT_PAGE_SIZE;
            hasMore = (Boolean)staffPageList.get("hasMore");
        }
        return count;
    }

    private CorpStaffVO mergeDepartmentProperties(CorpStaffVO dbStaff, CorpStaffVO targetStaff) {
        Map<Long, Long> dbOrderInDept = dbStaff.getOrderInDepts();
        Map<Long, Long> newOrderInDept = targetStaff.getOrderInDepts();
        Set<Long> deptSet = dbOrderInDept.keySet();
        for(Long deptId : deptSet){
            newOrderInDept.put(deptId, dbOrderInDept.get(deptId));
        }

        Map<Long, Boolean> dbIsLeaderInDept = dbStaff.getIsLeaderInDepts();
        Map<Long, Boolean> newIsLeaderInDept = targetStaff.getIsLeaderInDepts();
        for(Long deptId : dbIsLeaderInDept.keySet()){
            newIsLeaderInDept.put(deptId, dbIsLeaderInDept.get(deptId));
        }
        return targetStaff;
    }
}
