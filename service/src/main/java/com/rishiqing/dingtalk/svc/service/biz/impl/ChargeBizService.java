package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.svc.constant.SystemConstant;
import com.rishiqing.dingtalk.svc.converter.order.OrderConverter;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderStatusVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.order.OrderManager;
import com.rishiqing.dingtalk.api.service.rsq.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-16 16:23
 */
public class ChargeBizService {
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private CorpManager corpManager;
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
        orderManager.saveOrUpdateOrderStatus(orderStatus);

        // 读取corp
        CorpVO corp = corpManager.getCorpByCorpId(corpId);
        //  当充值时，如果公司还没有同步成功，那么就先不作处理
        if (corp == null || corp.getRsqId() == null) {
            return;
        }

        rsqAccountBizService.doRsqCharge(orderStatus);

        // 更新OrderStatusDO的状态为rsq_sync
        orderStatus.setStatus(SystemConstant.ORDER_STATUS_RSQ_SYNC);
        orderManager.saveOrUpdateOrderStatus(orderStatus);

        // 保存CorpChargeStatusDO
        CorpChargeStatusVO corpChargeStatusDO = OrderConverter.orderStatusVO2CorpChargeStatusVO(orderStatus);
        corpChargeStatusDO.setStatus(SystemConstant.ORDER_CORP_CHARGE_STATUS_OK);
        // 计算当前人数，先暂时指定公司总人数为1，保证不超员，以后改为每天查人数来更新这个字段
        corpChargeStatusDO.setTotalQuantity(1L);
        orderManager.saveOrUpdateCorpChargeStatus(corpChargeStatusDO);
    }
}
