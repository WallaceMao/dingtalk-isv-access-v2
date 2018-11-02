package com.rishiqing.dingtalk.biz.isv.service;

import com.rishiqing.dingtalk.biz.isv.model.SuiteVO;

/**
 * 套件VO的增删改查
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public interface SuiteManageService {
    /**
     * 获取suite的基本信息
     * @param key
     * @return
     */
    SuiteVO getSuiteInfoByKey(String key);
}
