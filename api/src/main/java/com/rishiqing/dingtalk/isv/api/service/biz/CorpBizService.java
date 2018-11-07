package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 17:42
 */
public interface CorpBizService {
    void activateCorpApp(CorpAuthInfoVO corpAuthInfo);

    void changeCorpApp(String corpId);

    void relieveCorpApp(String corpId);

    void prepareChargeCorpApp(OrderEventVO orderEvent);

    void chargeCorpApp(Long orderId);
}
