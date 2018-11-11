package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.http.CorpRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:40
 */
public class DeptService {
    private static Long DEFAULT_ROOT_DEPT_ID = 1L;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private CorpRequestHelper corpRequestHelper;

    /**
     * 获取部门的逻辑上这样的：
     * 1.  遍历deptIdList
     * 1.1.  如果deptId是根部门（deptId为1），那么递归获取全量部门
     * 1.2.  如果deptId不是跟部门，则只获取该部门的详情，
     * @param corpId
     * @param deptIdList
     */
    public void fetchAndSaveCorpDepartmentList(String corpId, List<Long> deptIdList){
        if(deptIdList == null){
            return;
        }
        for(Long deptId : deptIdList){
            //  递归读取所有子部门
            this.fetchAndSaveCorpDepartmentRecursive(corpId, deptId);
//            if(deptId.equals(DEFAULT_ROOT_DEPT_ID)){
//                this.fetchAndSaveCorpDepartmentRecursive(corpId, deptId);
//            }else{
//                this.fetchAndSaveCorpDepartment(corpId, deptId);
//            }
        }
    }

    private void fetchAndSaveCorpDepartment(String corpId, Long deptId) {
        CorpDepartmentVO dept = corpRequestHelper.getCorpDepartment(corpId, deptId);
        corpDepartmentManageService.saveOrUpdateCorpDepartment(dept);
    }

    /**
     * 递归调用获取并保存id为parentId的子部门
     * @param parentId
     * @param corpId
     * @return
     */
    private void fetchAndSaveCorpDepartmentRecursive(String corpId, Long parentId){
        //  先保存根部门
        this.fetchAndSaveCorpDepartment(corpId, parentId);
        List<CorpDepartmentVO> deptList = corpRequestHelper.getChildCorpDepartment(corpId, parentId);

        //  如果无子部门，那么就返回
        if(deptList == null || deptList.size() == 0){
            return;
        }

        //  递归保存子部门
        for(CorpDepartmentVO dept : deptList){
            this.fetchAndSaveCorpDepartmentRecursive(corpId, dept.getDeptId());
        }
    }
}
