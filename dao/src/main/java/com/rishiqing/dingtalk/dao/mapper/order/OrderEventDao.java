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
	void saveOrUpdateOrderEvent(OrderEventDO orderEventDO);

	/**
	 * 根据id查询event
	 * @param id
	 * @return
	 */
	OrderEventDO getOrderEventById(@Param("id") Long id);

	/**
	 * 获取一个公司最近一条orderEvent记录
	 * @param corpId
	 * @return
	 */
	OrderEventDO getLatestOrderEventByCorpId(@Param("corpId") String corpId);
}

