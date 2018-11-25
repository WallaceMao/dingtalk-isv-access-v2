package com.rishiqing.dingtalk.web.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;
import com.rishiqing.dingtalk.isv.api.service.biz.PopupBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:11
 */
@Controller
public class StaffPopupController {
    private static final Logger bizLogger = LoggerFactory.getLogger(StaffPopupController.class);

    @Autowired
    private PopupBizService popupBizService;

    /**
     * 获取企业充值信息
     * @param corpId
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/corp/user/popup", method = {RequestMethod.GET})
    public Map<String, Object> fetchCorpChargeInfo(
            @RequestParam("corpId") String corpId,
            @RequestParam("userId") String userId
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/corp/user/popup",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("userId", userId)
        ));
        try{
            PopupInfoVO popupInfo = popupBizService.getPopupInfo(corpId, userId);
            return HttpResult.getSuccess(convertPopoupInfo(popupInfo));
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/corp/user/popup",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("userId", userId)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 获取记录打卡静默期
     * @param corpId
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/corp/user/popup", method = {RequestMethod.POST})
    public Map<String, Object> submitPopup(
            @RequestParam("corpId") String corpId,
            @RequestParam("userId") String userId,
            @RequestBody JSONObject json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "POST /corp/user/popup",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("userId", userId),
                new LogFormatter.KeyValue("userId", json)
        ));
        try{
            String type = json.getString("popupType");
            popupBizService.logStaffPopup(corpId, userId, type);
            return HttpResult.getSuccess(null);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "POST /corp/user/popup",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("userId", userId),
                    new LogFormatter.KeyValue("userId", json)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 暂时放在这里，以后重构再挪地方
     * @param popupInfo
     * @return
     */
    private Map<String, Object> convertPopoupInfo(PopupInfoVO popupInfo){
        Map<String, Object> map = new HashMap<>();
        map.put("corpId", popupInfo.getCorpId());
        map.put("serviceExpire", popupInfo.getServiceExpire());
        map.put("buyNumber", popupInfo.getBuyNumber());
        map.put("totalNumber", popupInfo.getTotalNumber());
        map.put("isAdmin", popupInfo.getAdmin());
        map.put("specKey", popupInfo.getSpecKey());

        Map<String, Object> muteInfo = new HashMap<>();
        Map<String, StaffPopupConfigVO> configMap  = popupInfo.getPopupConfigMap();
        Map<String, StaffPopupLogVO> muteMap  = popupInfo.getMuteInfoMap();
        if(configMap != null){
            for(Map.Entry<String, StaffPopupConfigVO> entry : configMap.entrySet()){
                String type = entry.getKey();
                StaffPopupConfigVO configDO = entry.getValue();
                StaffPopupLogVO logDO = muteMap.get(type);

                Map<String, Object> objMap = new HashMap<>();
                if(!map.containsKey("saleQrCodeUrl")){
                    map.put("saleQrCodeUrl", configDO.getSaleQrCodeUrl());
                }
                if(!map.containsKey("salePhoneNumber")){
                    map.put("salePhoneNumber", configDO.getSalePhoneNumber());
                }
                objMap.put("muteExpire", logDO != null ? logDO.getPopupMuteExpire() : 0L);
                muteInfo.put(type, objMap);
            }
        }
        map.put("muteInfo", muteInfo);
        return map;
    }
}
