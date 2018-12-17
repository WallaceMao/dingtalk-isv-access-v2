package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.biz.service.util.StaffService;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class UserModifyOrgSyncActionHandler implements SyncActionHandler {
    @Autowired
    private StaffService staffService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    /**
     * @param data
     * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
     * subscribe_id  ： 套件suiteid加下划线0
     * copp_id : 开通套件微应用的企业corpid
     * biz_id             ： 员工userId
     * biz_data         ：数据为如下两种 Json格式：
     * 企业增加或修改员工，字段值来自于开放平台接口user/get 。
     * "syncAction": "user_add_org"，表示企业增加员工事件之后的员工信息
     * "syncAction": "user_modify_org" ,表示企业修改员事件之后的员工信息
     * "syncAction": "user_dept_change" ，表示企业修改员工所在部门事件之后的员工信息
     * "syncAction": "user_role_change"，表示企业修改员工所在角色(包括管理员变更)事件之后的员工信息
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        String corpId = data.getCorpId();
        String userId = data.getBizId();

        // 如果errcode不为0，例如50002，说明userId不在可见范围之内，那么此时删除该用户
        if (json.containsKey("errcode") && json.getLong("errcode") > 0) {
            staffService.deleteUserAndSubtractCount(corpId, userId);
            return;
        }
        CorpStaffVO corpStaffVO = SuiteDbCheckConverter.json2CorpStaff(json);
        corpStaffVO.setCorpId(corpId);
        // 这里要判断是不是userId是不是存在，如果不存在的话要走保存方法，如果存在的话走更新
        CorpStaffVO dbStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        if (dbStaff == null) {
            staffService.saveCorpStaffAndAddCount(corpStaffVO, new Date().getTime());
        } else {
            staffService.updateAndPushCorpStaff(corpStaffVO, new Date().getTime());
        }
    }
}
