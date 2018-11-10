package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgDeptModifySyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;

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
        String corpId = data.getCorpId();
        CorpDepartmentVO deptVO = SuiteDbCheckConverter.json2CorpDepartment(json);
        deptVO.setCorpId(corpId);
        corpDepartmentManageService.saveOrUpdateCorpDepartment(deptVO);
        deptVO = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, deptVO.getDeptId());

        //  然后推送到日事清
        rsqAccountBizService.updateRsqDepartment(deptVO);
    }
}
