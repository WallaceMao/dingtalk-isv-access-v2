package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order;

import com.rishiqing.dingtalk.api.model.domain.order.OrderStatusDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("orderStatusDao")
public interface OrderStatusDao {

	/**
	 * 保存orderStatusDO
	 * @param orderStatusDO
	 */
	public void saveOrUpdateOrderStatus(OrderStatusDO orderStatusDO);

	/**
	 * 根据orderId获取orderStatus
	 * @param orderId
	 * @return
	 */
	public OrderStatusDO getOrderStatusByOrderId(@Param("orderId") Long orderId);
}

