package com.rishiqing.dingtalk.biz.service.base.suite.impl;

import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteConverter;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteTicketConverter;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteTokenConverter;
import com.rishiqing.dingtalk.dao.mapper.suite.SuiteDao;
import com.rishiqing.dingtalk.dao.mapper.suite.SuiteTicketDao;
import com.rishiqing.dingtalk.dao.mapper.suite.SuiteTokenDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:48
 */
public class SuiteManageServiceImpl implements SuiteManageService {
    @Autowired
    private SuiteDao suiteDao;
    @Autowired
    private SuiteTicketDao suiteTicketDao;
    @Autowired
    private SuiteTokenDao suiteTokenDao;

    @Override
    public SuiteVO getSuite() {
        return SuiteConverter.suiteDO2SuiteVO(suiteDao.getSuite());
    }

    @Override
    public SuiteTicketVO getSuiteTicket() {
        return SuiteTicketConverter.suiteTicketDO2SuiteTicketVO(suiteTicketDao.getSuiteTicket());
    }

    @Override
    public void saveOrUpdateSuiteTicket(SuiteTicketVO suiteTicketVO) {
        suiteTicketDao.saveOrUpdateSuiteTicket(
                SuiteTicketConverter.suiteTicketVO2SuiteTicketDO(suiteTicketVO)
        );
    }

    @Override
    public SuiteTokenVO getSuiteToken() {
        return SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(suiteTokenDao.getSuiteToken());
    }

    @Override
    public void saveOrUpdateSuiteToken(SuiteTokenVO suiteTokenVO) {
        suiteTokenDao.saveOrUpdateSuiteToken(
                SuiteTokenConverter.suiteTokenVO2SuiteTokenDO(suiteTokenVO)
        );
    }
}
