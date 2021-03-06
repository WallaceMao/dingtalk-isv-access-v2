package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteChangeEvent;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.manager.suite.CorpSuiteAuthManager;
import com.rishiqing.dingtalk.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.SuiteCallbackBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 16:48
 */
public class SuiteCallbackBizServiceImpl implements SuiteCallbackBizService {
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpBizService corpBizService;
    @Autowired
    private CorpSuiteAuthManager corpSuiteAuthManager;
    @Autowired
    private AsyncEventBus asyncCorpSuiteAuthEventBus;
    @Autowired
    private AsyncEventBus asyncCorpSuiteChangeEventBus;

    /**
     * 接受到推送过来的suite ticket
     * @param suiteTicketVO
     */
    @Override
    public void receiveSuiteTicket(SuiteTicketVO suiteTicketVO){
        suiteManager.saveOrUpdateSuiteTicket(suiteTicketVO);
    }

    /**
     * 接受到临时授权码之后，要抛出异步事件，然后马上返回
     * @param corpSuiteAuth
     */
    @Override
    public void receiveTmpAuthCode(CorpSuiteAuthVO corpSuiteAuth){
        corpSuiteAuthManager.saveOrUpdateCorpSuiteAuth(corpSuiteAuth);

        //  注意，这里使用的eventBus需要时异步逻辑,加速套件开通时间.
        CorpSuiteAuthEvent corpSuiteAuthEvent = new CorpSuiteAuthEvent();
        corpSuiteAuthEvent.setSuiteKey(corpSuiteAuth.getSuiteKey());
        corpSuiteAuthEvent.setCorpId(corpSuiteAuth.getCorpId());
        corpSuiteAuthEvent.setPermanentCode(corpSuiteAuth.getPermanentCode());
        corpSuiteAuthEvent.setChPermanentCode(corpSuiteAuth.getChPermanentCode());
        asyncCorpSuiteAuthEventBus.post(corpSuiteAuthEvent);
    }

    @Override
    public void receiveSuiteAuth(CorpAuthInfoVO autoInfo){}

    /**
     * 接收到变更授权的事件
     * @param authCorpId
     */
    @Override
    public void receiveChangeAuth(String authCorpId){
        SuiteVO suiteVO = suiteManager.getSuite();
        CorpSuiteChangeEvent corpSuiteChangeEvent = new CorpSuiteChangeEvent();
        corpSuiteChangeEvent.setSuiteKey(suiteVO.getSuiteKey());
        corpSuiteChangeEvent.setCorpId(authCorpId);
        asyncCorpSuiteChangeEventBus.post(corpSuiteChangeEvent);
    }

    /**
     * 接收到解除授权的事件
     * @param authCorpId
     */
    @Override
    public void receiveSuiteRelieve(String authCorpId){
        corpBizService.relieveCorpApp(authCorpId);
    }

    @Override
    public void receiveMarketBuy(OrderEventVO orderEvent){
        corpBizService.prepareChargeCorpApp(orderEvent);
    }
}
