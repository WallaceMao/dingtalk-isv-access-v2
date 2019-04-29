package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.filter;

import com.rishiqing.dingtalk.api.model.domain.config.FilterConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:10
 */
@Repository("filterConfigDao")
public interface FilterConfigDao {
    FilterConfigDO getFilterConfigByFilterKey(@Param("filterKey") String filterKey);

    void saveOrUpdateFilterConfig(FilterConfigDO filterConfigDO);
}
