package com.rishiqing.dingtalk.mgr.dingmain.manager.suite;

import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthDeptVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthUserVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:25
 */
public interface CorpSuiteAuthManager {
    void saveOrUpdateCorpSuiteAuth(CorpSuiteAuthVO corpSuiteAuthVO);

    CorpSuiteAuthVO getCorpSuiteAuth(String corpId);

    CorpSuiteAuthDeptVO getCorpSuiteAuthDeptByCorpIdAndDeptId(
            String corpId, Long deptId);

    CorpSuiteAuthUserVO getCorpSuiteAuthUserByCorpIdAndUserId(
            String corpId, String userId);
}
