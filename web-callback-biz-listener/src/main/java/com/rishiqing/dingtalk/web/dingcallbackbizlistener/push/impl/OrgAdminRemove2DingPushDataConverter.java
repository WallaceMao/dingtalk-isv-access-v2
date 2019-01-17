package com.rishiqing.dingtalk.web.dingcallbackbizlistener.push.impl;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.svc.model.OpenSyncBizSyncAction;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.push.Callback2DingPushDataConverter;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.service.CallbackBizDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 23:48
 */
public class OrgAdminRemove2DingPushDataConverter implements Callback2DingPushDataConverter {
    @Autowired
    private CallbackBizDataService callbackBizDataService;

    @Override
    public List<OpenSyncBizDataDO> convert(CorpCallbackQueueDO callbackDO) {
        return callbackBizDataService.convertStaffCallback(callbackDO, OpenSyncBizSyncAction.Tag.USER_ROLE_CHANGE);
    }
}
