package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgChangedEvent;
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
public class RsqSuiteAuthChangePushEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger("LSN_RSQ_SUITE_CHANGE_AUTH_LOGGER");

    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private FailBizService failBizService;

    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpOrgChangedEvent(CorpOrgChangedEvent corpOrgChangedEvent) {
        try{
            String corpId = corpOrgChangedEvent.getCorpId();
            Long scopeVersion = corpOrgChangedEvent.getScopeVersion();
            //  先将信息同步到日事清
            rsqAccountBizService.syncAllChanged(corpId);
            //  再将本地删除
            corpDepartmentManageService.deleteCorpDepartmentByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
            corpStaffManageService.deleteCorpStaffByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
            // 最后，同步现有用户的管理员信息
            rsqAccountBizService.updateAllCorpAdmin(corpId, scopeVersion);
        }catch (Exception e){
            //  加入失败job,失败任务会重试
//            failBizService.saveCorpOrgSyncFail(corpOrgChangedEvent);
            bizLogger.error("corpOrgCreatedEvent: " + corpOrgChangedEvent, e);
        }
    }
}
