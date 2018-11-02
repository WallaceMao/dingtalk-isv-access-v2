package com.rishiqing.dingtalk.biz.isv.model;

import com.rishiqing.dingtalk.biz.isv.service.SuiteManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:40
 */
public class GlobalSuite {
    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;

    private SuiteVO suite;

    private void init(){
        //  读取套件基本信息
        String suiteKey = (String)isvGlobal.get("suiteKey");
        this.suite = suiteManageService.getSuiteInfoByKey(suiteKey);
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
