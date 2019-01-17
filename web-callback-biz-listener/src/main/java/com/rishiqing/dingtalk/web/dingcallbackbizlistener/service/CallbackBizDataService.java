package com.rishiqing.dingtalk.web.dingcallbackbizlistener.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestJsonHelper;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.svc.constant.SystemConstant;
import com.rishiqing.dingtalk.svc.model.OpenSyncBizSyncAction;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.constant.BizTypeConstant;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.constant.DingPushDataConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 21:45
 */
public class CallbackBizDataService {
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpRequestJsonHelper corpRequestJsonHelper;

    public List<OpenSyncBizDataDO> convertDeptCallback(CorpCallbackQueueDO callbackDO, OpenSyncBizSyncAction.Tag syncAction) {
        List<Long> array = parseDeptIdList(callbackDO.getEventJSON());
        List<OpenSyncBizDataDO> result = new ArrayList<>(array.size());

        for (Long deptId : array) {
            OpenSyncBizDataDO data = getBaseOpenSyncBizData(callbackDO);
            JSONObject bizData;
            //  org_dept_remove事件不做处理
            if (syncAction.equals(OpenSyncBizSyncAction.Tag.ORG_DEPT_REMOVE)) {
                bizData = new JSONObject();
            } else {
                bizData = fetchDeptBizData(callbackDO.getCorpId(), deptId);
            }
            bizData.put("syncAction", syncAction.getKey());
            data.setBizType(BizTypeConstant.DEPT);
            data.setBizId(String.valueOf(deptId));
            data.setBizData(JSON.toJSONString(bizData));

            result.add(data);
        }
        return result;
    }

    public List<OpenSyncBizDataDO> convertStaffCallback(CorpCallbackQueueDO callbackDO, OpenSyncBizSyncAction.Tag syncAction){
        List<String> array = parseUserIdList(callbackDO.getEventJSON());
        List<OpenSyncBizDataDO> result = new ArrayList<>(array.size());

        for (String userId : array) {
            OpenSyncBizDataDO data = getBaseOpenSyncBizData(callbackDO);
            JSONObject bizData;
            //  user_leave_remove事件不做处理
            if (syncAction.equals(OpenSyncBizSyncAction.Tag.USER_LEAVE_ORG)) {
                bizData = new JSONObject();
            } else {
                bizData = fetchStaffBizData(callbackDO.getCorpId(), userId);
            }

            bizData.put("syncAction", syncAction.getKey());
            data.setBizType(BizTypeConstant.STAFF);
            data.setBizId(userId);
            data.setBizData(JSON.toJSONString(bizData));

            result.add(data);
        }
        return result;
    }

    public List<OpenSyncBizDataDO> convertCorpCallback(CorpCallbackQueueDO callbackDO, OpenSyncBizSyncAction.Tag syncAction){
        OpenSyncBizDataDO data = getBaseOpenSyncBizData(callbackDO);
        List<OpenSyncBizDataDO> result = new ArrayList<>();
        String corpId = callbackDO.getCorpId();
        JSONObject bizData;
        //  user_leave_remove事件不做处理
        if (syncAction.equals(OpenSyncBizSyncAction.Tag.ORG_REMOVE)) {
            bizData = new JSONObject();
        } else {
            bizData = fetchCorpBizData(corpId);
        }

        bizData.put("syncAction", syncAction.getKey());
        data.setBizType(BizTypeConstant.CORP);
        data.setBizId(corpId);
        data.setBizData(JSON.toJSONString(bizData));

        result.add(data);
        return result;
    }

    private OpenSyncBizDataDO getBaseOpenSyncBizData(CorpCallbackQueueDO callbackDO) {
        String subscribeId = suiteManager.getSuite().getSuiteId() + DingPushDataConstant.SUBSCRIBE_ID_SURFIX;
        OpenSyncBizDataDO data = new OpenSyncBizDataDO();
        data.setSubscribeId(subscribeId);
        data.setCorpId(callbackDO.getCorpId());
        data.setOpenCursor(callbackDO.getTimestamp());
        data.setStatus(SystemConstant.OPEN_SYNC_BIZ_DATA_STATUS_DEFAULT);
        return data;
    }

    private JSONObject fetchDeptBizData(String corpId, Long deptId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        return corpRequestJsonHelper.getCorpDepartment(
                corpTokenVO.getCorpToken(), deptId);
    }

    private JSONObject fetchStaffBizData(String corpId, String staffId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        return corpRequestJsonHelper.getCorpStaff(corpTokenVO.getCorpToken(), staffId);
    }

    private JSONObject fetchCorpBizData(String corpId) {
        SuiteVO suiteVO = suiteManager.getSuite();
        SuiteTicketVO suiteTicketVO = suiteManager.getSuiteTicket();
        return corpRequestJsonHelper.getCorpInfo(
                suiteVO.getSuiteKey(),
                suiteVO.getSuiteSecret(),
                suiteTicketVO.getSuiteTicket(),
                corpId);
    }

    private List<Long> parseDeptIdList(String jsonString) {
        JSONObject eventJson = JSONObject.parseObject(jsonString);
        return JSONArray.parseArray(eventJson.getString("DeptId"), Long.class);
    }

    private List<String> parseUserIdList(String jsonString) {
        JSONObject eventJson = JSONObject.parseObject(jsonString);
        return JSONArray.parseArray(eventJson.getString("UserId"), String.class);
    }}
