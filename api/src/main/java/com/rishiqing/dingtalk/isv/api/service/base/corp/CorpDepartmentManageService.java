package com.rishiqing.dingtalk.isv.api.service.base.corp;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:23
 */
public interface CorpDepartmentManageService {
    CorpDepartmentVO getCorpDepartmentByCorpIdAndDeptId(String corpId, Long deptId);

    List<CorpDepartmentVO> getCorpDepartmentListByCorpId(String corpId);

    void saveOrUpdateCorpDepartment(CorpDepartmentVO dept);

    void updateRsqInfo(CorpDepartmentVO departmentVO);

    List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndParentId(String corpId, Long deptId);

    CorpDepartmentVO getTopCorpDepartment(String corpId);

    void deleteCorpDepartmentByCorpIdAndDeptId(String corpId, Long deptId);
}
