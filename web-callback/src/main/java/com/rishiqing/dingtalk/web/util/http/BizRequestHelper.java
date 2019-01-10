package com.rishiqing.dingtalk.web.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2019-01-09 1:34
 */
public class BizRequestHelper {
    private static Logger logger = LoggerFactory.getLogger(BizRequestHelper.class);

    @Autowired
    private HttpRequestHelper httpRequestHelper;

    /**
     * 获取永久授权码
     * @param suiteKey
     * @param tmpAuthCode
     * @param suiteAccessToken
     * @return
     */
    public CorpSuiteAuthVO getPermanentCode(String suiteKey, String tmpAuthCode, String suiteAccessToken) {
        try {
            String url = "https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("tmp_auth_code", tmpAuthCode);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (Long.valueOf(0).equals(errCode)) {
//                System.out.println(Thread.currentThread().getId() + ":success getPermanentCode----" + errCode);
                JSONObject corpObject = jsonObject.getJSONObject("auth_corp_info");
                String chPcode = StringUtils.isEmpty(jsonObject.getString("ch_permanent_code"))?"":jsonObject.getString("ch_permanent_code");
                String pCode = StringUtils.isEmpty(jsonObject.getString("permanent_code"))?"":jsonObject.getString("permanent_code");
                String corpId = corpObject.getString("corpid");
                CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
                corpSuiteAuthVO.setSuiteKey(suiteKey);
                corpSuiteAuthVO.setChPermanentCode(chPcode);
                corpSuiteAuthVO.setPermanentCode(pCode);
                corpSuiteAuthVO.setCorpId(corpId);
                return corpSuiteAuthVO;
            }else if(Long.valueOf(40078).equals(errCode)){
//                System.out.println(Thread.currentThread().getId() + ":temp auth code request result----" + errCode);
                //  fffffffffffffffffffuck
                //  由于钉钉的的原因，企业授权时会同时推送多个tmp_auth_code，而tmp_auth_code使用过一次就会失效
                //  因此在接受到多个tmp_auth_code做授权时，只能通过40078（不存在的临时授权码）的错误码判断
                //  收到40078后，不能返回错误，否则钉钉还会继续发送tmp_auth_code，
                //  而应该直接返回成功！

                //  不存在的临时授权码,不再继续重试
                return null;
            }
            return null;
        } catch (Exception e) {
            logger.error(LogFormatter.format(LogFormatter.LogEvent.END,
                    LogFormatter.getKV("accessToken", suiteAccessToken)
            ), e);
            return null;
        }
    }

    /**
     * 激活应用
     * @param suiteKey
     * @param corpId
     * @param suiteAccessToken
     */
    public void activeSuite(String suiteKey, String corpId, String suiteAccessToken) {
        try {
            String url = "https://oapi.dingtalk.com/service/activate_suite?suite_access_token=" + suiteAccessToken;
            Map<String, Object> parmMap = new HashMap<String, Object>();
            parmMap.put("suite_key", suiteKey);
            parmMap.put("auth_corpid", corpId);
            String sr = httpRequestHelper.httpPostJson(url, JSON.toJSONString(parmMap));
            JSONObject jsonObject = JSON.parseObject(sr);
            Long errCode = jsonObject.getLong("errcode");
            if (!Long.valueOf(0).equals(errCode)) {
                throw new BizRuntimeException("error: " + sr);
            }
        } catch (Exception e) {
            logger.error(LogFormatter.format(LogFormatter.LogEvent.END,
                    LogFormatter.getKV("accessToken", suiteAccessToken)
            ), e);
        }
    }
}
