package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.rishiqing.dingtalk.biz.service.biz.impl.DeptService;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgDeptRemoveSyncActionHandler implements SyncActionHandler {
    @Autowired
    private DeptService deptService;

    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ： 部门departmentid
     biz_data         ：数据为如下两种 Json格式
     企业增加或者修改部门，字段值来自于开放平台接口department/get。
     "syncAction": "org_dept_remove"，表示企业删除部门
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        String corpId = data.getCorpId();
        Long deptId = Long.valueOf(data.getBizId());
        // 需要做级联删除，即删除该部门下的所有子级部门
        deptService.pushAndDeleteCorpDepartmentRecursive(corpId, deptId);
    }
}
