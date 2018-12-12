package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.http.CorpRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.self.api.service.RsqAccountBizService;
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
    @Autowired
    private RsqAccountBizService rsqAccountBizService;

    /**
     * 获取部门的逻辑上这样的：
     * 1.  遍历deptIdList
     * 1.1.  如果deptId是根部门（deptId为1），那么递归获取全量部门
     * 1.2.  如果deptId不是跟部门，则只获取该部门的详情，
     *
     * @param corpId
     * @param deptIdList
     * @param scopeVersion
     */
    public void fetchAndSaveCorpDepartmentList(String corpId, List<Long> deptIdList, Long scopeVersion) {
        if (deptIdList == null) {
            return;
        }
        for (Long deptId : deptIdList) {
            //  递归读取所有子部门
            this.fetchAndSaveCorpDepartmentRecursive(corpId, deptId, scopeVersion);
//            if(deptId.equals(DEFAULT_ROOT_DEPT_ID)){
//                this.fetchAndSaveCorpDepartmentRecursive(corpId, deptId);
//            }else{
//                this.fetchAndSaveCorpDepartment(corpId, deptId);
//            }
        }
    }

    public void saveAndPushCorpDepartment(CorpDepartmentVO deptVO, Long scopeVersion) {
        deptVO.setScopeVersion(scopeVersion);
        corpDepartmentManageService.saveOrUpdateCorpDepartment(deptVO);

        //  然后推送到日事清
        rsqAccountBizService.createRsqDepartment(deptVO);
    }

    /**
     * 删除corpId和deptId下的所有子级部门
     *
     * @param corpId
     * @param deptId
     */
    public void deleteAndPushCorpDepartment(String corpId, Long deptId) {
        pushAndDeleteCorpDepartmentRecursive(corpId, deptId);
    }

    private void fetchAndSaveCorpDepartment(String corpId, Long deptId, Long scopeVersion) {
        CorpDepartmentVO dept = corpRequestHelper.getCorpDepartment(corpId, deptId);
        dept.setScopeVersion(scopeVersion);
        corpDepartmentManageService.saveOrUpdateCorpDepartment(dept);
    }

    /**
     * 递归调用获取并保存id为parentId的子部门。
     * 暂时不需要考虑处理人员关联，因为在钉钉中，如果一个部门的任意一个子级部门有成员，那么不允许删除。
     *
     * @param corpId
     * @param parentId
     * @param scopeVersion
     * @return
     */
    private void fetchAndSaveCorpDepartmentRecursive(String corpId, Long parentId, Long scopeVersion) {
        //  先保存根部门
        this.fetchAndSaveCorpDepartment(corpId, parentId, scopeVersion);
        List<CorpDepartmentVO> deptList = corpRequestHelper.getChildCorpDepartment(corpId, parentId);

        //  如果无子部门，那么就返回
        if (deptList == null || deptList.size() == 0) {
            return;
        }

        //  递归保存子部门
        for (CorpDepartmentVO dept : deptList) {
            this.fetchAndSaveCorpDepartmentRecursive(corpId, dept.getDeptId(), scopeVersion);
        }
    }

    private void pushAndDeleteCorpDepartmentRecursive(String corpId, Long parentDeptId) {
        List<Long> childDeptIdList =
                corpDepartmentManageService.listCorpDepartmentDeptIdByCorpIdAndParentId(
                        corpId, parentDeptId);
        if (childDeptIdList != null && childDeptIdList.size() > 0) {
            pushAndDeleteCorpDepartmentRecursive(corpId, parentDeptId);
        }
        pushAndDeleteSingleDepartment(corpId, parentDeptId);
    }

    private void pushAndDeleteSingleDepartment(String corpId, Long deptId) {
        //  先推送到日事清做删除
        CorpDepartmentVO deptVO = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        if (deptVO != null) {
            rsqAccountBizService.deleteRsqDepartment(deptVO);
            // 然后本地做删除
            corpDepartmentManageService.deleteCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        }
    }
}
