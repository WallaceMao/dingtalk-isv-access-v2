package com.rishiqing.dingtalk.svc.dingpush.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthDeptVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthUserVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpSuiteAuthManager;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.manager.CorpSyncActionManager;
import com.rishiqing.dingtalk.svc.service.biz.impl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Da Shuai
 * @create: 2019-04-29 10:50
 */
public class FilterCorpSyncActionManagerImpl implements CorpSyncActionManager {
    private static final String USER_ADD_ORG = "user_add_org";
    @Autowired
    @Qualifier("baseCorpSyncActionManager")
    private CorpSyncActionManager corpSyncActionManager;
    @Autowired
    private CorpSuiteAuthManager corpSuiteAuthManager;
    @Autowired
    private StaffService staffService;

    @Override
    public void handleSyncData(OpenSyncBizDataVO openSyncBizDataVO) {
        String corpId = openSyncBizDataVO.getCorpId();
        //获得同步行为
        JSONObject dataJsonObject = JSONObject.parseObject(openSyncBizDataVO.getBizData());
        String syncAction = SuiteDbCheckConverter.json2SyncActionString(dataJsonObject);
        //如果同步行为需要特殊处理
        if (USER_ADD_ORG.equals(syncAction)) {
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
            //判断企业员工是否超过阈值，超员直接抛出异常
            staffService.isCorpStaffCountAboveThreshold(userIdList, deptIdList, corpId);
            corpSyncActionManager.handleSyncData(openSyncBizDataVO);
        } else {
            corpSyncActionManager.handleSyncData(openSyncBizDataVO);
        }
    }
}
