package com.rishiqing.dingtalk.biz.isv.service.impl;

import com.rishiqing.dingtalk.biz.isv.converter.SuiteConverter;
import com.rishiqing.dingtalk.biz.isv.model.SuiteVO;
import com.rishiqing.dingtalk.biz.isv.service.SuiteManageService;
import com.rishiqing.dingtalk.dao.mapper.suite.SuiteDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:48
 */
public class SuiteManageServiceImpl implements SuiteManageService {
    @Autowired
    private SuiteDao suiteDao;
    @Override
    public SuiteVO getSuiteInfoByKey(String key) {
        return SuiteConverter.suiteDO2SuiteVO(suiteDao.getSuiteBySuiteKey(key));
    }
}
