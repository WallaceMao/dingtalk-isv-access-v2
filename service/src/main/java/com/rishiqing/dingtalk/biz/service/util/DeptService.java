package com.rishiqing.dingtalk.biz.service.util;

import com.alibaba.fastjson.JSON;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:40
 */
public class DeptService {
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;

    public void getAndSaveAllCorpOrg(String corpId, Long parentDeptId){
        //  先获取根部门
//        ServiceResult<DepartmentVO> rootSr = getDept(1L, corpId, suiteKey);
//        if(!rootSr.isSuccess()){
//            return ServiceResult.failure(rootSr.getCode(), rootSr.getMessage());
//        }
//        DepartmentVO root = rootSr.getResult();
//        System.out.println("result list:" + JSON.toJSON(root));
//        ServiceResult<Void> rootSaveSr = saveOrUpdateCorpDepartment(root);
//        if(!rootSaveSr.isSuccess()){
//            return ServiceResult.failure(rootSaveSr.getCode(), rootSaveSr.getMessage());
//        }
//
//        //  根据根部门递归获取子部门
//        ServiceResult<Void> sr = getAndSaveRecursiveSubDepartment(root.getDeptId(), corpId, suiteKey);
//
//        if(!sr.isSuccess()){
//            return ServiceResult.failure(sr.getCode(), sr.getMessage());
//        }
//
//        return ServiceResult.success(null);
    }
}
