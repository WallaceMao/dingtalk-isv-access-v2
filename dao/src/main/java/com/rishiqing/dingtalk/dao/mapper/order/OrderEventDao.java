package com.rishiqing.dingtalk.dao.mapper.order;

import com.rishiqing.dingtalk.dao.model.order.OrderEventDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("orderEventDao")
public interface OrderEventDao {
	/**
	 * 保存orderEvent
	 * @param orderEventDO
	 */
	public void saveOrUpdateOrderEvent(OrderEventDO orderEventDO);

	/**
	 * 根据id查询event
	 * @param id
	 * @return
	 */
	public OrderEventDO getOrderEventById(@Param("id") Long id);

	/**
	 * 获取一个公司最近一条orderEvent记录
	 * @param corpId
	 * @return
	 */
	public OrderEventDO getLatestOrderEventByCorpId(@Param("corpId") String corpId);
}

