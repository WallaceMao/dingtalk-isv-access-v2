package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.service.biz.impl.StaffService;
import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class UserDeptChangeSyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private CorpDepartmentManager corpDepartmentManager;
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
        String userId = data.getBizId();

        // 如果errcode不为0，例如50002，说明userId不在可见范围之内，那么此时删除该用户
        if (json.containsKey("errcode") && json.getLong("errcode") > 0) {
            staffService.deleteUserAndSubtractCount(corpId, userId);
            return;
        }

        CorpStaffVO corpStaffVO = SuiteDbCheckConverter.json2CorpStaff(json);
        corpStaffVO.setCorpId(corpId);
        // 需要判断是加人、减人还是维持不变，逻辑如下：
        // 加人：如果corpId和userId在系统中原本不存在，那么就直接加人
        // 减人：如果corpId和userId在系统中存在，但是新的deptIdList全部都不在可见范围之内，那么就减人
        // 维持不变，如果corpId和userId在系统中存在，且deptIdList中至少有一个deptId在可见范围之内，那么就只更新deptIdList列表
        CorpStaffVO dbStaff = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);
        if (dbStaff == null) {
            // 加人
            staffService.saveCorpStaffAndAddCount(corpStaffVO, new Date().getTime());
        } else {
            // 保存用户信息
            staffService.updateAndPushCorpStaff(corpStaffVO, new Date().getTime());
        }
        // boolean toDelete = true;
        // for (Long deptId: corpStaffVO.getDepartment()) {
        //     CorpDepartmentVO corpDept = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        //     if (corpDept != null) {
        //         toDelete = false;
        //         break;
        //     }
        // }
        // if (toDelete) {
        //     staffService.deleteUserAndSubtractCount(corpId, userId);
        // } else {
        //     staffService.updateAndPushCorpStaff(dbStaff);
        // }
    }
}
