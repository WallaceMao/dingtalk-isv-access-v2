package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order;

import com.rishiqing.dingtalk.api.model.domain.order.OrderRsqPushEventDO;
import org.springframework.stereotype.Repository;

@Repository("orderRsqPushEventDao")
public interface OrderRsqPushEventDao {

	/**
	 * 保存orderRsqPushEventDO
	 * @param orderRsqPushEventDO
	 */
	public void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventDO orderRsqPushEventDO);
}

