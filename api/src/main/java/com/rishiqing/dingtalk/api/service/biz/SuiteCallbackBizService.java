package com.rishiqing.dingtalk.api.service.biz;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;

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
