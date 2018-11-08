package com.rishiqing.dingtalk.biz.http;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 13:45
 */
public interface CorpRequestHelper {
    CorpDepartmentVO getCorpDepartment(String corpId, Long deptId);

    List<CorpDepartmentVO> getChildCorpDepartment(String corpId, Long parentDeptId);

    CorpStaffVO getCorpStaff(String corpId, String userId);

    Map<String, Object> getCorpDepartmentStaffByPage(String corpId, Long deptId, Long offset, Long size);
}
