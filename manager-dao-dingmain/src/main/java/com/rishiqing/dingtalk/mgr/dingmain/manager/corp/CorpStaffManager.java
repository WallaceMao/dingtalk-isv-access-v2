package com.rishiqing.dingtalk.mgr.dingmain.manager.corp;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:24
 */
public interface CorpStaffManager {
    void saveOrUpdateCorpStaff(CorpStaffVO staff);

    void deleteCorpStaffByCorpIdAndScopeVersionLessThan(String corpId, Long lessThanScopeVersion);

    CorpStaffVO getCorpStaffByCorpIdAndUserId(String corpId, String userId);

    void updateRsqInfo(CorpStaffVO staffVO);

    List<CorpStaffVO> getCorpStaffListByCorpId(String corpId);

    List<String> getCorpStaffUserIdListByCorpId(String corpId);

    List<CorpStaffVO> getCorpStaffListByCorpIdAndIsAdmin(String corpId, Boolean isAdmin);

    void deleteCorpStaffByCorpIdAndUserId(String corpId, String userId);

    CorpStaffVO getCorpStaffByCorpIdAndUserIdAndScopeVersion(
            String corpId, String userId, Long scopeVersion);

    List<CorpStaffVO> getCorpStaffListByCorpIdAndScopeVersion(
            String corpId, Long scopeVersion);

    List<CorpStaffVO> getCorpStaffListByCorpIdAndScopeVersionLessThan(
            String corpId, Long scopeVersion);

    List<CorpStaffVO> getCorpStaffListByCorpIdAndIsAdminAndScopeVersion(
            String corpId, Boolean isAdmin, Long scopeVersion);

    List<CorpStaffVO> listCorpStaffByCorpIdAndDepartment(String corpId, Long deptId);

    Long countCorpStaffByCorpId(String corpId);
}
