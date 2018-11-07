package com.rishiqing.dingtalk.biz.service.base.suite.impl;

import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.biz.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:26
 */
public class CorpSuiteAuthManageServiceImpl implements CorpSuiteAuthManageService {
    @Autowired
    private CorpSuiteAuthDao corpSuiteAuthDao;

    @Override
    public void saveOrUpdateCorpSuiteAuth(CorpSuiteAuthVO corpSuiteAuthVO) {
        corpSuiteAuthDao.saveOrUpdateCorpSuiteAuth(
                CorpSuiteAuthConverter.CorpSuiteAuthVO2CorpSuiteAuthDO(corpSuiteAuthVO)
        );
    }

    @Override
    public CorpSuiteAuthVO getCorpSuiteAuth(String corpId){
        return CorpSuiteAuthConverter.CorpSuiteAuthDO2CorpSuiteAuthVO(
                corpSuiteAuthDao.getCorpSuiteAuthByCorpId(corpId)
        );
    }
}
