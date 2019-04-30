package com.rishiqing.dingtalk.svc.service.config.impl;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;
import com.rishiqing.dingtalk.api.service.filter.FilterService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.filter.FilterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:49
 */
@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterManager filterManager;

    @Override
    public Boolean isCorpStaffAboveThreshold(String corpId) {
        CorpSyncFilterDO corpSyncFilterByCorpId = filterManager.getCorpSyncFilterByCorpId(corpId);
        return corpSyncFilterByCorpId != null;
    }
}
