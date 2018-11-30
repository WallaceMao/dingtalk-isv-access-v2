package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.biz.constant.SystemConstant;
import com.rishiqing.dingtalk.biz.converter.order.OrderConverter;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderStatusVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.order.OrderManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-16 16:23
 */
public class ChargeBizService {
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    /**
     * 充值的主方法，流程如下：
     * 1. 保存OrderStatusDO，初始状态为paid
     * 2. 保存OrderRsqPushEventDO，初始状态为pending
     * 3. 发送后台接口进行充值，充值成功后返回，失败后做报错处理，单独日志打印
     * 4. 更新OrderRsqPushEventDO的状态为success
     * 5. 更新OrderStatusDO的状态为rsq_sync
     * 6. 保存CorpChargeStatusDO
     * @return
     */
    public void charge(OrderEventVO orderEventVO){
        String corpId = orderEventVO.getBuyCorpId();

        // 保存order status
        OrderStatusVO orderStatus = OrderConverter.orderEventVO2OrderStatusVO(orderEventVO);
        orderStatus.setStatus(SystemConstant.ORDER_STATUS_PAID);
        orderManageService.saveOrUpdateOrderStatus(orderStatus);

        // 读取corp
        CorpVO corp = corpManageService.getCorpByCorpId(corpId);
        if(corp == null){
            throw new BizRuntimeException("corp not found: " + orderEventVO);
        }
        if(corp.getRsqId() == null){
            throw new BizRuntimeException("corp rsqId not found: " + orderEventVO);
        }

        rsqAccountBizService.doRsqCharge(orderStatus);

        // 更新OrderStatusDO的状态为rsq_sync
        orderStatus.setStatus(SystemConstant.ORDER_STATUS_RSQ_SYNC);
        orderManageService.saveOrUpdateOrderStatus(orderStatus);

        // 保存CorpChargeStatusDO
        CorpChargeStatusVO corpChargeStatusDO = OrderConverter.orderStatusVO2CorpChargeStatusVO(orderStatus);
        corpChargeStatusDO.setStatus(SystemConstant.ORDER_CORP_CHARGE_STATUS_OK);
        // 计算当前人数，先暂时指定公司总人数为1，保证不超员，以后改为每天查人数来更新这个字段
        corpChargeStatusDO.setTotalQuantity(1L);
        orderManageService.saveOrUpdateCorpChargeStatus(corpChargeStatusDO);
    }
}
