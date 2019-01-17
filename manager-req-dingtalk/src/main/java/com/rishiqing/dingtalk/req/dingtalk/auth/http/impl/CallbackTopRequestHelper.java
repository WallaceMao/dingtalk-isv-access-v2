package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CallbackRequestHelper;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackFailResultVO;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackUrlVO;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 回调方式中需要使用的接口，在钉钉云方式中一下接口均已不再使用
 * @author Wallace Mao
 * Date: 2019-01-15 0:13
 */
public class CallbackTopRequestHelper implements CallbackRequestHelper {
    private static final Logger logger = LoggerFactory.getLogger(CallbackTopRequestHelper.class);
    /**
     * 获取永久授权码
     *
     * @param suiteKey
     * @param tmpAuthCode
     * @param suiteAccessToken
     * @return
     */
    @Override
    public CorpSuiteAuthVO getPermanentCode(String suiteKey, String corpId, String tmpAuthCode, String suiteAccessToken) {
        logger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "http request getPermanentCode",
                LogFormatter.getKV("tmpAuthCode", tmpAuthCode),
                LogFormatter.getKV("corpId", corpId),
                LogFormatter.getKV("suiteAccessToken", suiteAccessToken)));
        // DingtalkClient不支持suiteAccessToken，url暂时只能先这么写，直接把suite_access_token拼在后面
        DingTalkClient client = new DefaultDingTalkClient(
                "https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token="
                        + suiteAccessToken);
        OapiServiceGetPermanentCodeRequest request = new OapiServiceGetPermanentCodeRequest();
        request.setTmpAuthCode(tmpAuthCode);
        try {
            OapiServiceGetPermanentCodeResponse response = client.execute(request, suiteAccessToken);
            Long errCode = response.getErrcode();
            if (Long.valueOf(0).equals(errCode)) {
                CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
                corpSuiteAuthVO.setSuiteKey(suiteKey);
                corpSuiteAuthVO.setChPermanentCode(response.getChPermanentCode());
                corpSuiteAuthVO.setPermanentCode(response.getPermanentCode());
                corpSuiteAuthVO.setCorpId(corpId);
                return corpSuiteAuthVO;
            } else if (Long.valueOf(40078).equals(errCode)) {
                //  fffffffffffffffffffuck
                //  由于钉钉的的原因，企业授权时会同时推送多个tmp_auth_code，而tmp_auth_code使用过一次就会失效
                //  因此在接受到多个tmp_auth_code做授权时，只能通过40078（不存在的临时授权码）的错误码判断
                //  收到40078后，不能返回错误，否则钉钉还会继续发送tmp_auth_code，
                //  而应该直接返回成功！

                //  不存在的临时授权码,不再继续重试
                return null;
            } else {
                checkResult(response.getErrcode(), response.getErrmsg());
                return null;
            }
        } catch (ApiException e) {
            throw new BizRuntimeException("getPermanentCode error: ", e);
        }
    }

    /**
     * 激活应用
     *
     * @param suiteKey
     * @param corpId
     * @param suiteAccessToken
     */
    @Override
    public void activeSuite(String suiteKey, String corpId, String suiteAccessToken) {
        logger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "http request activeSuite",
                LogFormatter.getKV("suiteKey", suiteKey),
                LogFormatter.getKV("corpId", corpId),
                LogFormatter.getKV("suiteAccessToken", suiteAccessToken)));
        // DingtalkClient不支持suiteAccessToken，url暂时只能先这么写，直接把suite_access_token拼在后面
        DingTalkClient client = new DefaultDingTalkClient(
                "https://oapi.dingtalk.com/service/activate_suite?suite_access_token="
                + suiteAccessToken);
        OapiServiceActivateSuiteRequest request = new OapiServiceActivateSuiteRequest();
        request.setSuiteKey(suiteKey);
        request.setAuthCorpid(corpId);
        try {
            OapiServiceActivateSuiteResponse response = client.execute(request, suiteAccessToken);
            checkResult(response.getErrcode(), response.getErrmsg());
        } catch (ApiException e) {
            throw new BizRuntimeException("activeSuite error: ", e);
        }
    }

    /**
     * 注册回调事件的url
     * @link https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5
     * @param accessToken
     * @param callbackTagList
     * @param token
     * @param aesKey
     * @param url
     * @return
     */
    @Override
    public void registerCallbackUrl(
            String accessToken, List<String> callbackTagList, String token, String aesKey, String url) {
        logger.info(LogFormatter.format(
                        LogFormatter.LogEvent.START,
                        "registerCallbackUrl",
                        LogFormatter.getKV("accessToken", accessToken),
                        LogFormatter.getKV("callbackTagList", callbackTagList),
                        LogFormatter.getKV("token", token),
                        LogFormatter.getKV("aesKey", aesKey),
                        LogFormatter.getKV("url", url)));
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl(url);
        request.setAesKey(aesKey);
        request.setToken(token);
        request.setCallBackTag(callbackTagList);
        try {
            OapiCallBackRegisterCallBackResponse response = client.execute(request, accessToken);
            checkResult(response.getErrcode(), response.getErrmsg());
        } catch (ApiException e) {
            throw new BizRuntimeException("registerCallbackUrl: ", e);
        }
    }

    /**
     * 查询回调事件的url
     * @link https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5
     * @param accessToken
     * @return
     */
    @Override
    public EventCallbackUrlVO queryCallbackUrl(String accessToken) {
        logger.info(LogFormatter.format(
                        LogFormatter.LogEvent.START,
                        "queryCallbackUrl",
                        LogFormatter.getKV("accessToken", accessToken)));

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackGetCallBackResponse response = client.execute(request, accessToken);
            Long errCode = response.getErrcode();
            // {"errmsg":"回调地址已不存在","errcode":71007}
            if (Long.valueOf(71007).equals(errCode)) {
                return null;
            }
            checkResult(errCode, response.getErrmsg());
            return convertToEventCallbackUrlVO(response);
        } catch (ApiException e) {
            throw new BizRuntimeException("queryCallbackUrl", e);
        }
    }

    /**
     * 更新回调事件的url
     * @link https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5
     * @param accessToken
     * @param callbackTagList
     * @param token
     * @param aesKey
     * @param url
     * @return
     */
    @Override
    public void updateCallbackUrl(
            String accessToken, List<String> callbackTagList, String token, String aesKey, String url) {
        logger.info(
                LogFormatter.format(
                        LogFormatter.LogEvent.START,
                        "updateCallbackUrl",
                        LogFormatter.getKV("accessToken", accessToken),
                        LogFormatter.getKV("callbackTagList", callbackTagList),
                        LogFormatter.getKV("token", token),
                        LogFormatter.getKV("aesKey", aesKey),
                        LogFormatter.getKV("url", url)));
        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/update_call_back");
        OapiCallBackUpdateCallBackRequest request = new OapiCallBackUpdateCallBackRequest();
        request.setUrl(url);
        request.setAesKey(aesKey);
        request.setToken(token);
        request.setCallBackTag(callbackTagList);
        try {
            OapiCallBackUpdateCallBackResponse response = client.execute(request,accessToken);
            checkResult(response.getErrcode(), response.getErrmsg());
        } catch (ApiException e) {
            throw new BizRuntimeException("updateCallbackUrl: ", e);
        }
    }

    /**
     * 删除回调接口
     * @link https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5
     * @param accessToken
     * @return
     */
    @Override
    public void deleteCallbackUrl(String accessToken) {
        logger.info(LogFormatter.format(
                        LogFormatter.LogEvent.START,
                        "deleteCallbackUrl",
                        LogFormatter.getKV("accessToken", accessToken)));

        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/delete_call_back");
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackDeleteCallBackResponse response = client.execute(request,accessToken);
            checkResult(response.getErrcode(), response.getErrmsg());
        } catch (ApiException e) {
            throw new BizRuntimeException("deleteCallbackUrl", e);
        }
    }

    /**
     * 查询失败的回调
     * @link https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5
     * @param accessToken
     * @return
     */
    @Override
    public EventCallbackFailResultVO getCallbackFailedResult(String accessToken) {
        logger.info(LogFormatter.format(
                        LogFormatter.LogEvent.START,
                        "getCallbackFailedResult",
                        LogFormatter.getKV("accessToken", accessToken)));

        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back_failed_result");
        OapiCallBackGetCallBackFailedResultRequest request = new OapiCallBackGetCallBackFailedResultRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackGetCallBackFailedResultResponse response = client.execute(request,accessToken);
            checkResult(response.getErrcode(), response.getErrmsg());
            return convertToEventCallbackFailResultVO(response);
        } catch (ApiException e) {
            throw new BizRuntimeException("getCallbackFailedResult: ", e);
        }
    }

    private EventCallbackUrlVO convertToEventCallbackUrlVO(OapiCallBackGetCallBackResponse response) {
        EventCallbackUrlVO eventCallbackUrlVO = new EventCallbackUrlVO();
        eventCallbackUrlVO.setErrcode(response.getErrcode());
        eventCallbackUrlVO.setErrmsg(response.getErrmsg());
        eventCallbackUrlVO.setCallbackTag(response.getCallBackTag());
        eventCallbackUrlVO.setToken(response.getToken());
        eventCallbackUrlVO.setAesKey(response.getAesKey());
        eventCallbackUrlVO.setUrl(response.getUrl());
        return eventCallbackUrlVO;
    }

    private EventCallbackFailResultVO convertToEventCallbackFailResultVO(OapiCallBackGetCallBackFailedResultResponse response) {
        EventCallbackFailResultVO result = new EventCallbackFailResultVO();
        result.setErrcode(response.getErrcode());
        result.setErrmsg(response.getErrmsg());
        result.setHasMore(response.getHasMore());
        if (response.getFailedList() != null) {
            List<OapiCallBackGetCallBackFailedResultResponse.Failed> failedList = response.getFailedList();
            List<EventCallbackFailResultVO.EventCallbackVO> callbackVOList = new ArrayList<>(failedList.size());
            for (OapiCallBackGetCallBackFailedResultResponse.Failed failed : failedList) {
                EventCallbackFailResultVO.EventCallbackVO callbackVO = new EventCallbackFailResultVO.EventCallbackVO();
                callbackVO.setCallbackTag(failed.getCallBackTag());
                callbackVO.setEventTime(failed.getEventTime());
                callbackVO.setData(failed.getData());
                callbackVOList.add(callbackVO);
            }
            result.setFailedList(callbackVOList);
        }
        return result;
    }

    private void checkResult(Long errCode, String errMsg){
        if (!Long.valueOf(0).equals(errCode)) {
            throw new BizRuntimeException(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    LogFormatter.getKV("errcode", errCode),
                    LogFormatter.getKV("errmsg", errMsg)
            ));
        }
    }
}
