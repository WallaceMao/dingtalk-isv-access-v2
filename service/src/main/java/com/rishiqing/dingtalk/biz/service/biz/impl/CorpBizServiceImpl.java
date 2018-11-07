package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.rishiqing.dingtalk.biz.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.biz.converter.suite.CorpAppConverter;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpAppManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 跟公司相关的上层业务服务，用来处理开通应用等业务
 * @author Wallace Mao
 * Date: 2018-11-03 17:43
 */
public class CorpBizServiceImpl implements CorpBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger("CORP_SUITE_AUTH_LOGGER");

    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private EventBus asyncCorpOrgSyncEventBus;

    /**
     * 激活应用。本方法只处理将企业部门以及人员信息从钉钉抓取到auth server。
     * 分为如下几步：
     * 1. 获取企业基本信息（实际上是授权信息），如果没有通讯录可见范围的话要获取通讯录可见范围
     * 2. 保存企业基本信息
     * 3. 根据通讯录可见范围保存部门
     * 4. 获取用户，保存用户
     * 5. eventBus抛出事件，进行后续处理（推送到日事清等）
     * @param corpAuthInfo
     */
    @Override
    public void activateCorpApp(CorpAuthInfoVO corpAuthInfo) {
        SuiteTokenVO suiteTokenVO = suiteManageService.getSuiteToken();
        String suiteToken = suiteTokenVO.getSuiteToken();
        String suiteKey = suiteTokenVO.getSuiteKey();

        String corpId = corpAuthInfo.getAuthCorpInfo().getCorpId();
        if(corpId == null){
            throw new BizRuntimeException("corpId is null, corpAuthInfo is: " + corpAuthInfo);
        }
        //  1. 如果corpAuthInfo中authInfo的授权信息不存在，那么重新获取授权信息
        if(corpAuthInfo.getAuthInfo() == null){
            //TODO  根据corpId获取authInfo，走钉钉的获取授权信息的接口
        }
        //  2. 如果corpAuthInfo中的authScope不存在，那么重新获取可见范围信息
        if(corpAuthInfo.getAuthScope() == null){
            //TODO  获取企业的可见范围
        }
        //  在这里corpAuthInfo是完整的包含可见范围和授权的企业信息的对象

        //  3.  保存公司信息
        corpManageService.saveOrUpdateCorp(
                CorpConverter.corpAuthInfoVO2CorpVO(corpAuthInfo)
        );
        //  4.  保存corpApp的关联信息
        List<CorpAppVO> corpAppVOList = CorpAppConverter.corpAuthInfoVO2AppVOList(corpAuthInfo);
        for(CorpAppVO corpAppVO : corpAppVOList){
            corpAppManageService.saveOrUpdateCorpApp(corpAppVO);
        }
        //  5.  根据可见范围获取部门和员工信息


        //  6.  异步，更新钉钉的组织机构以及用户信息到本地，然后与ISV更新组织机构和人员信息
        CorpOrgSyncEvent corpOrgSyncEvent = new CorpOrgSyncEvent();
        corpOrgSyncEvent.setSuiteKey(suiteKey);
        corpOrgSyncEvent.setCorpId(corpId);
        asyncCorpOrgSyncEventBus.post(corpOrgSyncEvent);



//        //2.激活
//        try {
////            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
//            isvService.activateSuite(suiteToken, suiteKey, corpId);
//        } catch (Exception e) {
//            if (e instanceof com.dingtalk.open.client.common.ServiceException) {
//                if (41030 == ((ServiceException) e).getCode()) {
//                    //企业未对该套件授权,终止,返回成功
//                    return ServiceResult.success(null);
//                }
//            }else{
//                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
//            }
//        }
//
//        //3.更新企业信息
//        ServiceResult<Void> getCorpInfoSr = this.getCorpInfo(suiteToken,suiteKey,corpId, permanentCode);
//        if(!getCorpInfoSr.isSuccess()){
//            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
//        }
//        //4.注册或者更新回调，在通讯录或者群会话发生变更时会调用此接口
////            ServiceResult<Void> saveCallBackSr = this.saveCorpCallback(suiteKey, corpId, (accessSystemConfig.getCorpSuiteCallBackUrl() + suiteKey), SuiteCallBackMessage.Tag.getAllTag());
////            if(!saveCallBackSr.isSuccess()){
////                return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
////            }
//
//        //4.  获取用户的授权范围，需要根据授权范围获取部门和人员
//
//        //5.  更新部门及人员信息
//        //5.1 同步部门
//        ServiceResult<Void> deptSr = deptManageService.getAndSaveAllCorpOrg(suiteKey, corpId);
//        if(!deptSr.isSuccess()){
//            return ServiceResult.failure(deptSr.getCode(),deptSr.getMessage());
//        }
//
//        //5.2 同步人员
//        ServiceResult<Void> staffSr = staffManageService.getAndSaveAllCorpOrgStaff(suiteKey, corpId);
//        if(!staffSr.isSuccess()){
//            return ServiceResult.failure(deptSr.getCode(),deptSr.getMessage());
//        }
//
//        //6.  异步，更新钉钉的组织机构以及用户信息到本地，然后与ISV更新组织机构和人员信息
//        CorpOrgSyncEvent corpOrgSyncEvent = new CorpOrgSyncEvent();
//        corpOrgSyncEvent.setSuiteKey(suiteKey);
//        corpOrgSyncEvent.setCorpId(corpId);
//        asyncCorpOrgSyncEventBus.post(corpOrgSyncEvent);
//
////            //7.发送mq到各个业务方,告知一个企业对套件授权了,业务方自己去做对应的业务
////            如果Queue尚未实现，此处需要注释掉，否则将会堵塞线程，影响eventBus的！
////            jmsTemplate.send(corpAuthSuiteQueue,new CorpAuthSuiteMessage(corpId,suiteKey, CorpAuthSuiteMessage.Tag.Auth));
//        return ServiceResult.success(null);
    }

    /**
     * 变更授权
     * @param corpId
     */
    @Override
    public void changeCorpApp(String corpId){}

    /**
     * 解除授权
     * @param corpId
     */
    @Override
    public void relieveCorpApp(String corpId){}

    /**
     * 收到充值事件，做充值前的准备工作
     * @param orderEvent
     */
    @Override
    public void prepareChargeCorpApp(OrderEventVO orderEvent){}

    /**
     * 进行充值
     * @param orderId
     */
    @Override
    public void chargeCorpApp(Long orderId){}
}
