package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackFailResultVO;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackUrlVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 0:12
 */
public interface CallbackRequestHelper {
    CorpSuiteAuthVO getPermanentCode(String suiteKey, String corpId, String tmpAuthCode, String suiteAccessToken);

    void activeSuite(String suiteKey, String corpId, String suiteAccessToken);

    void registerCallbackUrl(
            String accessToken, List<String> callbackTagList, String token, String aesKey, String url);

    EventCallbackUrlVO queryCallbackUrl(String accessToken);

    void updateCallbackUrl(
            String accessToken, List<String> callbackTagList, String token, String aesKey, String url);

    void deleteCallbackUrl(String accessToken);

    EventCallbackFailResultVO getCallbackFailedResult(String accessToken);
}
