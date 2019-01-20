package com.rishiqing.dingtalk.svc.model;

import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:40
 */
public class GlobalSuite {
    @Autowired
    private SuiteManager suiteManager;

    private SuiteVO suite;

    private void init(){
        this.suite = suiteManager.getSuite();
    }

    public Long getId() {
        return this.suite.getId();
    }

    public String getSuiteName() {
        return this.suite.getSuiteName();
    }

    public String getSuiteKey() {
        return this.suite.getSuiteKey();
    }

    public String getSuiteSecret() {
        return this.suite.getSuiteSecret();
    }

    public String getEncodingAesKey() {
        return this.suite.getEncodingAesKey();
    }

    public String getToken() {
        return this.suite.getToken();
    }

    public String getRsqAppName() {
        return this.suite.getRsqAppName();
    }

    public String getRsqAppToken() {
        return this.suite.getRsqAppToken();
    }

    public String getSuiteId() {
        return this.suite.getSuiteId();
    }
}
