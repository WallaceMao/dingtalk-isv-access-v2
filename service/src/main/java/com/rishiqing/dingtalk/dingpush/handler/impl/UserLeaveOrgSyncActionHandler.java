package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class UserLeaveOrgSyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    /**
     * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
    subscribe_id  ： 套件suiteid加下划线0
    copp_id : 开通套件微应用的企业corpid
    biz_id             ： 员工userId
    biz_data         ：数据为如下两种 Json格式：
    企业增加或修改员工，字段值来自于开放平台接口user/get 。
    "syncAction": "user_leave_org"，表示企业删除员工
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        String corpId = data.getCorpId();
        String userId = data.getBizId();
        CorpStaffVO corpStaffVO = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        corpStaffManageService.deleteCorpStaffByCorpIdAndUserId(corpId, userId);

        //  推送到日事清
        rsqAccountBizService.removeRsqTeamStaff(corpStaffVO);
    }
}
