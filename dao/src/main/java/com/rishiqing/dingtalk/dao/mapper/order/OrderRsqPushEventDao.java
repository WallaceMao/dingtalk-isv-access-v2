package com.rishiqing.dingtalk.dao.mapper.order;

import com.rishiqing.dingtalk.dao.model.order.OrderRsqPushEventDO;
import org.springframework.stereotype.Repository;

@Repository("orderRsqPushEventDao")
public interface OrderRsqPushEventDao {

	/**
	 * 保存orderRsqPushEventDO
	 * @param orderRsqPushEventDO
	 */
	void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventDO orderRsqPushEventDO);
}

