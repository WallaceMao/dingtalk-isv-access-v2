package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.http.CorpRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:48
 */
public class StaffService {
    private static long DEFAULT_PAGE_SIZE = 10;
    @Autowired
    private CorpRequestHelper corpRequestHelper;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;

    /**
     * 根据userIdList中的id，从钉钉处获取用户，然后保存到本地
     * @param corpId
     * @param userIdList
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
    public void fetchAndSaveAllCorpDepartmentStaff(String corpId, Long scopeVersion){
        List<CorpDepartmentVO> deptList = corpDepartmentManageService.getCorpDepartmentListByCorpIdAndScopeVersion(corpId, scopeVersion);
        for(CorpDepartmentVO dept : deptList){
            this.fetchAndSaveCorpDepartmentStaff(corpId, dept.getDeptId(), scopeVersion);
        }
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

    private void fetchAndSaveCorpStaff(String corpId, String userId, Long scopeVersion){
        CorpStaffVO staff = corpRequestHelper.getCorpStaff(corpId, userId);
        if(scopeVersion != null){
            staff.setScopeVersion(scopeVersion);
        }
        corpStaffManageService.saveOrUpdateCorpStaff(staff);
    }

    private void fetchAndSaveCorpDepartmentStaff(String corpId, Long deptId, Long scopeVersion){
        boolean hasMore = true;
        long offset = 0;
        while(hasMore){
            Map<String, Object> staffPageList = corpRequestHelper.getCorpDepartmentStaffByPage(
                    corpId, deptId, offset, DEFAULT_PAGE_SIZE
            );
            //  保存用户
            List<CorpStaffVO> staffList = (List<CorpStaffVO>)staffPageList.get("list");
            for(CorpStaffVO corpStaff : staffList){
                //  保存用户的时候要注意，orderInDepts和isLeaderInDepts这两个属性，这里得到的CorpStaffVO中的值只是在这个部门中的顺序号和是否是leader，
                //  如果之前就存在这个属性，那么要做合并，而不能直接覆盖
                CorpStaffVO dbStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, corpStaff.getUserId());
                if(dbStaff != null){
                    //  如果存在，需要将orderInDepts和isLeaderInDepts这两个属性合并
                    corpStaff = this.mergeDepartmentProperties(dbStaff, corpStaff);

                }
                if(scopeVersion != null){
                    corpStaff.setScopeVersion(scopeVersion);
                }
                corpStaffManageService.saveOrUpdateCorpStaff(corpStaff);
            }

            offset ++;
            hasMore = (Boolean)staffPageList.get("hasMore");
        }
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
