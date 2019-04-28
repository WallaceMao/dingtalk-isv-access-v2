package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthDeptVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthUserVO;
import com.rishiqing.dingtalk.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.dingtalk.api.service.config.ConfigService;
import com.rishiqing.dingtalk.api.service.util.OpenGlobalLockService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpSuiteAuthManager;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.handler.CorpSyncActionManager;
import com.rishiqing.dingtalk.svc.dingpush.handler.SuiteSyncActionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public class SuiteDbCheckBizServiceImpl implements SuiteDbCheckBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger(SuiteDbCheckBizServiceImpl.class);

    private static final String DB_CHECK_LOCK_KEY = "auth_check";
    private static final String DB_CHECK_MEDIUM_LOCK_KEY = "auth_medium_check";
    //企业授权同步行为：1."org_suite_auth" ：表示企业授权套件  2."org_suite_change"：表示企业变更授权范围
    private static final String[] ORG_SUITE_ARR = new String[]{"org_suite_auth", "org_suite_change"};
    //添加员工同步行为
    private static final String USER_ADD_ORG = "user_add_org";
    @Autowired
    private OpenGlobalLockService openGlobalLockService;
    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;
    @Autowired
    private SuiteSyncActionManager suiteSyncActionManager;
    @Autowired
    private CorpSyncActionManager corpSyncActionManager;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpSuiteAuthManager corpSuiteAuthManager;

    /**
     * 新增如下:
     * 功能：人数限制
     * 场景：用户授权开通微应用的时候
     * 逻辑：调用获取公司人数的接口，如果超过人数限制，不再进行同步，标记这个公司的人数过大，标记钉钉云push（open_sync_biz_data）的状态为-2；
     * 在isv_corp_sync_filter (id,  gmt_create, gmt_modified, corp_id, count, status )
     */
    @Override
    public void checkDingPushEvent() {
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if (lock == null) {
            return;
        }
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataListByStatus(0L);
            for (OpenSyncBizDataVO openSyncBizDataVO : syncList) {
                try {
                    //同步行为：授权时(检查企业人数)特殊处理
                    //特殊处理ORG_SUITE_ARR内的同步行为
                    handleDingSyncAction(openSyncBizDataVO);
                } catch (Exception e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云重要推送消息失败",
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    openSyncBizDataVO.setStatus(-1L);
                }
                openSyncBizDataManager.updateStatus(openSyncBizDataVO);
            }
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingPushEvent error"
            ), e);
        } finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_LOCK_KEY);
        }
    }

    /**
     * 新增功能：限制人数
     * 用户变更通讯录的时候：通讯录回调（open_sync_biz_data_medium）的时候，当查到这个公司属于“人数过大”时，标记钉钉云push的状态为-2
     */
    @Override
    public void checkDingMediumPushEvent() {
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if (lock == null) {
            return;
        }
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataMediumListByStatus(0L);
            for (OpenSyncBizDataVO openSyncBizDataVO : syncList) {
                try {
                    //员工添加时做特殊处理
                    handleDingMediumSyncAction(openSyncBizDataVO);
                } catch (Exception e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云普通推送消息失败",
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    openSyncBizDataVO.setStatus(-1L);
                }
                openSyncBizDataManager.updateMediumStatus(openSyncBizDataVO);
            }
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingMediumPushEvent error"
            ), e);
        } finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        }
    }

    /**
     * 高优先级同步行为处理
     * 特殊处理：企业授权应用时人数过滤功能
     *
     * @param openSyncBizDataVO
     */
    private void handleDingSyncAction(OpenSyncBizDataVO openSyncBizDataVO) {
        String corpId = openSyncBizDataVO.getCorpId();
        //获得同步行为
        JSONObject dataJsonObject = JSONObject.parseObject(openSyncBizDataVO.getBizData());
        String syncAction = SuiteDbCheckConverter.json2SyncActionString(dataJsonObject);
        //如果同步行为需要特殊处理
        if (Arrays.binarySearch(ORG_SUITE_ARR, syncAction) >= 0) {
            //根据企业id获得企业授权信息
            CorpAuthInfoVO corpAuthInfoVO = SuiteDbCheckConverter.json2CorpSuiteAuthInfo(dataJsonObject);
            CorpAuthScopeInfoVO.AuthOrgScopes authOrgScopes = corpAuthInfoVO.getAuthScope().getAuthOrgScopes();
            //企业授权的部门id列表
            List<Long> authedDept = authOrgScopes.getAuthedDept();
            //企业授权的员工userId列表
            List<String> authedUser = authOrgScopes.getAuthedUser();
            //统计总授权员工数
            Long corpStaffCount = staffService.getCorpStaffCountByCorpAuthScopeInfo(authedUser, authedDept, corpId);
            //检查是否高于阈值
            Boolean staffCountAboveThreshold = configService.isStaffCountAboveThreshold(corpStaffCount);
            //高于阈值
            if (staffCountAboveThreshold) {
                //todo 插入表isv_corp_sync_filter记录
                CorpSyncFilterDO corpSyncFilterDO = new CorpSyncFilterDO();
                corpSyncFilterDO.setCorpId(corpId);
                corpSyncFilterDO.setCount(corpStaffCount);
                corpSyncFilterDO.setStatus(syncAction + " above staff_count_threshold");
                corpManager.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
                openSyncBizDataVO.setStatus(-2L);
            } else {
                suiteSyncActionManager.handleSyncData(openSyncBizDataVO);
                openSyncBizDataVO.setStatus(1L);
            }
        } else {
            suiteSyncActionManager.handleSyncData(openSyncBizDataVO);
            openSyncBizDataVO.setStatus(1L);
        }
    }

    /**
     * 低优先级同步行为处理
     * 特殊处理：企业添加员工时人数过滤功能
     *
     * @param openSyncBizDataVO
     */
    private void handleDingMediumSyncAction(OpenSyncBizDataVO openSyncBizDataVO) {
        String corpId = openSyncBizDataVO.getCorpId();
        //获得同步行为
        JSONObject dataJsonObject = JSONObject.parseObject(openSyncBizDataVO.getBizData());
        String syncAction = SuiteDbCheckConverter.json2SyncActionString(dataJsonObject);
        //如果同步行为需要特殊处理
        if (USER_ADD_ORG.equals(syncAction)) {
            Long corpStaffCount = getAuthedStaffCount(corpId);
            Boolean staffCountAboveThreshold = configService.isStaffCountAboveThreshold(corpStaffCount);
            //如果高于阈值
            if (staffCountAboveThreshold) {
                //todo 插入表isv_corp_sync_filter记录
                CorpSyncFilterDO corpSyncFilterDO = new CorpSyncFilterDO();
                corpSyncFilterDO.setCorpId(corpId);
                corpSyncFilterDO.setCount(corpStaffCount);
                corpSyncFilterDO.setStatus(syncAction + " above staff_count_threshold");
                corpManager.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
                openSyncBizDataVO.setStatus(-2L);
            } else {
                corpSyncActionManager.handleSyncData(openSyncBizDataVO);
                openSyncBizDataVO.setStatus(1L);
            }
        } else {
            corpSyncActionManager.handleSyncData(openSyncBizDataVO);
            openSyncBizDataVO.setStatus(1L);
        }
    }
    @Override
    public Long getAuthedStaffCount(String corpId){
        //查询当前企业可见范围
        List<CorpSuiteAuthDeptVO> corpSuiteAuthDeptVOS = corpSuiteAuthManager.listGetCorpSuiteAuthDeptByCorpId(corpId);
        List<Long> deptIdList = new ArrayList<>(corpSuiteAuthDeptVOS.size());
        for (CorpSuiteAuthDeptVO corpSuiteAuthDeptVO : corpSuiteAuthDeptVOS) {
            deptIdList.add(Long.parseLong(corpSuiteAuthDeptVO.getDeptId()));
        }
        List<CorpSuiteAuthUserVO> corpSuiteAuthUserVOS = corpSuiteAuthManager.listGetCorpSuiteAuthUserByCorpId(corpId);
        List<String> userIdList = new ArrayList<>(corpSuiteAuthUserVOS.size());
        for (CorpSuiteAuthUserVO corpSuiteAuthUserVO : corpSuiteAuthUserVOS) {
            userIdList.add(corpSuiteAuthUserVO.getUserId());
        }
        //统计总授权员工数
        return staffService.getCorpStaffCountByCorpAuthScopeInfo(userIdList, deptIdList, corpId);
    }
}
