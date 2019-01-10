package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.biz.service.biz.impl.DeptService;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgDeptCreateSyncActionHandler implements SyncActionHandler {
    @Autowired
    private DeptService deptService;


    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ： 部门departmentid
     biz_data         ：数据为如下两种 Json格式
     企业增加或者修改部门，字段值来自于开放平台接口department/get。
     "syncAction": "org_dept_create"，表示部门创建
     "syncAction": "org_dept_modify"，表示部门修改
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        // 如果errcode不为0，例如50004，说明deptId不在可见范围之内，那么不需要操作直接返回
        if (json.containsKey("errcode") && json.getLong("errcode") > 0) {
            return;
        }
        String corpId = data.getCorpId();
        CorpDepartmentVO deptVO = SuiteDbCheckConverter.json2CorpDepartment(json);
        deptVO.setCorpId(corpId);
        deptService.saveAndPushCorpDepartment(deptVO, new Date().getTime());
    }
}
