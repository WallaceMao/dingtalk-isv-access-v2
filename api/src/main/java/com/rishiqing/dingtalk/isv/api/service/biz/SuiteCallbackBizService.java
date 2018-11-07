package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 16:46
 */
public interface SuiteCallbackBizService {
    void receiveSuiteTicket(SuiteTicketVO suiteTicketVO);

    void receiveTmpAuthCode(CorpSuiteAuthVO corpSuiteAuthVO);

    void receiveSuiteAuth(CorpAuthInfoVO autoInfo);

    void receiveChangeAuth(String authCorpId);

    void receiveSuiteRelieve(String authCorpId);

    void receiveMarketBuy(OrderEventVO orderEvent);
}
