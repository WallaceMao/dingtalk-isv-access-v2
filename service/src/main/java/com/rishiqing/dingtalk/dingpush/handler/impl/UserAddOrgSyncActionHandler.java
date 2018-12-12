package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.biz.service.util.StaffService;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class UserAddOrgSyncActionHandler implements SyncActionHandler {
    @Autowired
    private StaffService staffService;

    /**
     * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 开通套件微应用的企业corpid
     biz_id             ： 员工userId
     biz_data         ：数据为如下两种 Json格式：
     企业增加或修改员工，字段值来自于开放平台接口user/get 。
     "syncAction": "user_add_org"，表示企业增加员工事件之后的员工信息
     "syncAction": "user_modify_org" ,表示企业修改员事件之后的员工信息
     "syncAction": "user_dept_change" ，表示企业修改员工所在部门事件之后的员工信息
     "syncAction": "user_role_change"，表示企业修改员工所在角色(包括管理员变更)事件之后的员工信息
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        String corpId = data.getCorpId();
        CorpStaffVO corpStaffVO = SuiteDbCheckConverter.json2CorpStaff(json);
        corpStaffVO.setCorpId(corpId);
        staffService.saveCorpStaffAndAddCount(corpStaffVO, new Date().getTime());
    }
}
