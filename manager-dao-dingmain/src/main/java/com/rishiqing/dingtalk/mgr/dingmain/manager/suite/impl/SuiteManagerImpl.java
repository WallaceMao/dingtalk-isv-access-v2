package com.rishiqing.dingtalk.mgr.dingmain.manager.suite.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.suite.SuiteConverter;
import com.rishiqing.dingtalk.mgr.dingmain.converter.suite.SuiteTicketConverter;
import com.rishiqing.dingtalk.mgr.dingmain.converter.suite.SuiteTokenConverter;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.SuiteDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.SuiteTicketDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.SuiteTokenDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:48
 */
public class SuiteManagerImpl implements SuiteManager {
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
