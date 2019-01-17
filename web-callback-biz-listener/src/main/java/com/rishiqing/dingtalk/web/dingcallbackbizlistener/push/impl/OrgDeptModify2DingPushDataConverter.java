package com.rishiqing.dingtalk.web.dingcallbackbizlistener.push.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.svc.constant.SystemConstant;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.constant.BizTypeConstant;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.constant.DingPushDataConstant;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.push.Callback2DingPushDataConverter;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.service.CallbackBizDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 23:48
 */
public class OrgDeptModify2DingPushDataConverter implements Callback2DingPushDataConverter {
    private static final String OPEN_SYNC_BIZ_SYNC_ACTION = "org_dept_modify";
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CallbackBizDataService callbackBizDataService;

    @Override
    public List<OpenSyncBizDataDO> convert(CorpCallbackQueueDO callbackDO) {
        JSONObject eventJson = JSONObject.parseObject(callbackDO.getEventJSON());
        List<Long> array = JSONArray.parseArray(eventJson.getString("DeptId"), Long.class);
        List<OpenSyncBizDataDO> result = new ArrayList<>(array.size());
        String subscribeId = suiteManager.getSuite().getSuiteId() + DingPushDataConstant.SUBSCRIBE_ID_SURFIX;
        String corpId = callbackDO.getCorpId();
        Long timestamp = callbackDO.getTimestamp();
        for (Long deptId : array) {
            OpenSyncBizDataDO data = new OpenSyncBizDataDO();
            data.setSubscribeId(subscribeId);
            data.setCorpId(corpId);
            data.setBizId(String.valueOf(deptId));
            data.setBizType(BizTypeConstant.DEPT);
            JSONObject bizData = callbackBizDataService.fetchDeptBizData(corpId, deptId);
            bizData.put("syncAction", OPEN_SYNC_BIZ_SYNC_ACTION);
            data.setBizData(JSON.toJSONString(bizData));
            data.setOpenCursor(timestamp);
            data.setStatus(SystemConstant.OPEN_SYNC_BIZ_DATA_STATUS_DEFAULT);
            result.add(data);
        }
        return result;
    }
}
