package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 13:45
 */
public interface CorpRequestHelper {
    CorpDepartmentVO getCorpDepartment(String token, String corpId, Long deptId);

    List<CorpDepartmentVO> getChildCorpDepartment(String token, String corpId, Long parentDeptId);

    CorpStaffVO getCorpStaff(String token, String corpId, String userId);

    Map<String, Object> getCorpDepartmentStaffByPage(String token, String corpId, Long deptId, Long offset, Long size);

    CorpStaffVO getCorpStaffByAuthCode(String token, String corpId, String authCode);
}
