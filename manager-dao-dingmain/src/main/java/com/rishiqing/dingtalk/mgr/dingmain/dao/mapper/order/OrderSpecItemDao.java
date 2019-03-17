package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order;

import com.rishiqing.dingtalk.api.model.domain.order.OrderSpecItemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("orderSpecItemDao")
public interface OrderSpecItemDao {

	/**
	 * 保存orderSpecItemDO
	 * @param orderSpecItemDO
	 */
	public void saveOrUpdateOrderSpecItem(OrderSpecItemDO orderSpecItemDO);

	/**
	 * 根据goodsCode和itemCode获取规格
	 * @param goodsCode
	 * @param itemCode
	 * @return
	 */
	public OrderSpecItemDO getOrderSpecItemByGoodsCodeAndItemCode(@Param("goodsCode") String goodsCode, @Param("itemCode") String itemCode);
}

