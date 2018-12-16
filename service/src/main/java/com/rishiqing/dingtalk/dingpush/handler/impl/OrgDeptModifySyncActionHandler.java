package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.biz.service.util.DeptService;
import com.rishiqing.dingtalk.biz.service.util.StaffService;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgDeptModifySyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;
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
        Long deptId = Long.valueOf(data.getBizId());
        Long timestamp = new Date().getTime();

        // 如果errcode不为0，例如50004，说明department不在可见范围之内，那么此时删除该部门
        if (json.containsKey("errcode") && json.getLong("errcode") > 0) {
            // 删除步骤如下：
            // 首先如果corpId和deptId不存在，说明是在可见范围之外移动，那么就不做任何处理。
            // 如果corpId和deptId存在，那么按照如下步骤进行
            // 1. 判断该部门极其子部门下的所有员工，如果员工不在可见范围之内，那么就删除员工
            // 注意：判断员工在可见范围之内的方法：
            // （1）员工所在的部门，任意一个在可见范围之内，那么该员工就在可见范围之内
            // （2）如果员工所有的所在部门都不在可见范围之内，那么判断员工是不是直接在可见范围之内
            // 2. 递归删除部门

            CorpDepartmentVO departmentVO = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
            if (departmentVO == null) {
                // 说明是在可见范围之外移动，那么不做任何处理
                return;
            }

            // 说明是从可见范围之内移动到可见范围之外，需要对部门和成员做特殊处理
            // 首先处理员工，需要将不再可见范围之内的员工做删除操作
            staffService.deleteCorpDepartmentStaffNotInScopeRecursive(corpId, deptId, timestamp);
            // 然后需要处理部门，需要将部门及其子部门做递归删除操作
            deptService.pushAndDeleteCorpDepartmentRecursive(corpId, deptId);
            return;
        }

        CorpDepartmentVO deptVO = SuiteDbCheckConverter.json2CorpDepartment(json);
        deptVO.setCorpId(corpId);
        // 分两种情况：
        // 1. 如果是将部门从可见范围之外移动到可见范围之内，那么保存新的部门（递归保存子部门），保存子部门的所有员工，并推送到日事清后台。
        //   由于部门原本没在可见范围之内，所以移动之后需要将部门及子部门所有的员工都做保存。
        //   注意：有些跨部门的员工可能已经存在，那么就直接更新
        // 2. 如果是在可见范围之内移动部门，那么更新部门的parentId，并推送到日事清后台。
        //   （由于部门原本就已经在可见范围之内了，所以移动后不需要对部门员工做任何操作）
        CorpDepartmentVO dbDepartment = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        if (dbDepartment == null) {
            // 说明是从可见范围之外移动到可见范围之内
            // 1. 首先递归保存所有的子部门
            deptService.fetchAndSaveCorpDepartmentRecursive(corpId, deptId, timestamp);
            // 2. 保存deptId极其子部门下的所有用户
            staffService.fetchAndSaveCorpDepartmentStaffRecursive(corpId, deptId, timestamp);
            // 3. 推送到日事清创建所有子部门（递归创建）
            rsqAccountBizService.createRecursiveSubDepartmentByScopeVersion(deptVO, timestamp);
            // 4. 推送到日事清创建所有子部门的用户（注意，并不是所有子部门的用户都是新建的，也有可能用户是已经存在的）
            rsqAccountBizService.createCorpStaffByScopeVersion(corpId, timestamp);
        } else {
            // 说明是可见范围内做移动
            deptService.updateAndPushCorpDepartment(deptVO, timestamp);
        }
    }
}
