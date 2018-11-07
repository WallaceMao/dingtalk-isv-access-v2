package com.rishiqing.dingtalk.isv.api.service.base.suite;

import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:25
 */
public interface CorpSuiteAuthManageService {
    public void saveOrUpdateCorpSuiteAuth(CorpSuiteAuthVO corpSuiteAuthVO);

    CorpSuiteAuthVO getCorpSuiteAuth(String corpId);
}
