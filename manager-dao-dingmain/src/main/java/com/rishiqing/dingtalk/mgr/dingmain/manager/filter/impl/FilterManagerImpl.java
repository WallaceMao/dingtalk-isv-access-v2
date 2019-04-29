package com.rishiqing.dingtalk.mgr.dingmain.manager.filter.impl;

import com.rishiqing.dingtalk.api.model.domain.config.FilterConfigDO;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.filter.CorpSyncFilterDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.filter.FilterConfigDao;
import com.rishiqing.dingtalk.mgr.dingmain.manager.filter.FilterManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 18:02
 */
public class FilterManagerImpl implements FilterManager {
    @Autowired
    private FilterConfigDao filterConfigDao;
    @Autowired
    private CorpSyncFilterDao corpSyncFilterDao;

    @Override
    public FilterConfigDO getFilterConfigByFilterKey(String filterKey) {
        return filterConfigDao.getFilterConfigByFilterKey(filterKey);
    }

    @Override
    public void saveOrUpdateFilterConfig(FilterConfigDO filterConfigDO) {
        filterConfigDao.saveOrUpdateFilterConfig(filterConfigDO);
    }

    @Override
    public void saveOrUpdateCorpSyncFilter(CorpSyncFilterDO corpSyncFilterDO) {
        corpSyncFilterDao.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
    }

    @Override
    public CorpSyncFilterDO getCorpSyncFilterByCorpId(String corpId) {
        return corpSyncFilterDao.getCorpSyncFilterByCorpId(corpId);
    }
}
