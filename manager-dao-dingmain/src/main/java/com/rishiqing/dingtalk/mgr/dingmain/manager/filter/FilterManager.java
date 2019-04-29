package com.rishiqing.dingtalk.mgr.dingmain.manager.filter;

import com.rishiqing.dingtalk.api.model.domain.config.FilterConfigDO;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 18:01
 */
public interface FilterManager {
    FilterConfigDO getFilterConfigByFilterKey(String filterKey);

    void saveOrUpdateFilterConfig(FilterConfigDO filterConfigDO);

    void saveOrUpdateCorpSyncFilter(CorpSyncFilterDO corpSyncFilterDO);

    CorpSyncFilterDO getCorpSyncFilterByCorpId(String corpId);
}
