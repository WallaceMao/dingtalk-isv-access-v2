package com.rishiqing.dingtalk.api.service.biz;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 17:42
 */
public interface CorpBizService {
    void activateCorpApp(CorpAuthInfoVO corpAuthInfo, Long timestamp);

    void changeCorpApp(CorpAuthInfoVO corpAuthInfo, Long timestamp);

    void relieveCorpApp(String corpId);

    void prepareChargeCorpApp(OrderEventVO orderEvent);


    void chargeCorpApp(OrderEventVO orderEvent);
}
