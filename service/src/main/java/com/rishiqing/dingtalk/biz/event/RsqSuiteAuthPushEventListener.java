package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgCreatedEvent;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:14
 */
public class RsqSuiteAuthPushEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger(RsqSuiteAuthPushEventListener.class);

    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    @Autowired
    private FailBizService failBizService;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpOrgSyncEvent(CorpOrgCreatedEvent corpOrgCreatedEvent) {
        try{
            String corpId = corpOrgCreatedEvent.getCorpId();
            Long scopeVersion = corpOrgCreatedEvent.getScopeVersion();
            rsqAccountBizService.syncAllCreated(corpId);
            //  再将本地删除
            corpDepartmentManageService.deleteCorpDepartmentByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
            corpStaffManageService.deleteCorpStaffByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
            // 最后，同步现有用户的管理员信息
            rsqAccountBizService.updateAllCorpAdmin(corpId, scopeVersion);
        }catch (Exception e){
            //  加入失败job,失败任务会重试
            failBizService.saveCorpOrgSyncFail(corpOrgCreatedEvent);
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "RsqSuiteAuthPushEventListener",
                    new LogFormatter.KeyValue("corpOrgSyncEvent", corpOrgCreatedEvent)
            ), e);
        }
    }
}
