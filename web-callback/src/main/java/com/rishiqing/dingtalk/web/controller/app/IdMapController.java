package com.rishiqing.dingtalk.web.controller.app;

import com.alibaba.fastjson.JSONArray;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.isv.api.model.front.IdMapStaffVO;
import com.rishiqing.dingtalk.manager.front.IdMapStaffManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:11
 */
@Controller
public class IdMapController {
    private static final Logger bizLogger = LoggerFactory.getLogger(IdMapController.class);
    @Autowired
    private IdMapStaffManager idMapStaffManager;

    @ResponseBody
    @RequestMapping(value = "/idmap/userid2rsqid", method = {RequestMethod.POST})
    public Map<String, Object> userId2RsqId(
            @RequestParam("corpid") String corpId,
            @RequestBody JSONArray json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/idmap/userid2rsqid",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("json", json)
        ));
        try{
            List<IdMapStaffVO> list = idMapStaffManager.getRsqIdFromUserId(corpId, json.toArray(new String[]{}));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", list);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/idmap/userid2rsqid",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("json", json)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/idmap/rsqid2userid", method = {RequestMethod.POST})
    public Map<String, Object> rsqId2UserId(
            @RequestParam("corpid") String corpId,
            @RequestBody JSONArray json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/idmap/rsqid2userid",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("json", json)
        ));
        try{
            Object[] objArray = json.toArray();
            String[] idArray = new String[objArray.length];
            for(int i=0;i<objArray.length;i++){
                idArray[i] = String.valueOf(objArray[i]);
            }
            List<IdMapStaffVO> list = idMapStaffManager.getUserIdFromRsqId(corpId, idArray);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", list);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/idmap/rsqid2userid",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("json", json)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
