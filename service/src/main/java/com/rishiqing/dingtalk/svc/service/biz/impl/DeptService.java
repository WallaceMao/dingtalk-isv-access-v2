package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestHelper;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.api.service.rsq.RsqAccountBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:40
 */
public class DeptService {
    private static Long DEFAULT_ROOT_DEPT_ID = 1L;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpDepartmentManager corpDepartmentManager;
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
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptVO);

        //  然后推送到日事清
        rsqAccountBizService.createRsqDepartment(deptVO);
    }

    /**
     * 更新的deptVO
     * @param deptVO
     * @param scopeVersion
     */
    public void updateAndPushCorpDepartment(CorpDepartmentVO deptVO, Long scopeVersion) {
        deptVO.setScopeVersion(scopeVersion);
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptVO);
        deptVO = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(deptVO.getCorpId(), deptVO.getDeptId());

        //  然后推送到日事清
        rsqAccountBizService.updateRsqDepartment(deptVO);
    }

    private void fetchAndSaveCorpDepartment(String corpId, Long deptId, Long scopeVersion) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        CorpDepartmentVO dept = corpRequestHelper.getCorpDepartment(corpTokenVO.getCorpToken(), corpId, deptId);
        dept.setScopeVersion(scopeVersion);
        corpDepartmentManager.saveOrUpdateCorpDepartment(dept);
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
    public void fetchAndSaveCorpDepartmentRecursive(String corpId, Long parentId, Long scopeVersion) {
        //  先保存根部门
        this.fetchAndSaveCorpDepartment(corpId, parentId, scopeVersion);
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        List<CorpDepartmentVO> deptList = corpRequestHelper.getChildCorpDepartment(
                corpTokenVO.getCorpToken(), corpId, parentId);

        //  如果无子部门，那么就返回
        if (deptList == null || deptList.size() == 0) {
            return;
        }

        //  递归保存子部门
        for (CorpDepartmentVO dept : deptList) {
            this.fetchAndSaveCorpDepartmentRecursive(corpId, dept.getDeptId(), scopeVersion);
        }
    }

    public void pushAndDeleteCorpDepartmentRecursive(String corpId, Long parentDeptId) {
        List<Long> childDeptIdList =
                corpDepartmentManager.listCorpDepartmentDeptIdByCorpIdAndParentId(
                        corpId, parentDeptId);
        if (childDeptIdList != null && childDeptIdList.size() > 0) {
            for (Long childDeptId : childDeptIdList) {
                pushAndDeleteCorpDepartmentRecursive(corpId, childDeptId);
            }
        }
        pushAndDeleteSingleDepartment(corpId, parentDeptId);
    }

    private void pushAndDeleteSingleDepartment(String corpId, Long deptId) {
        //  先推送到日事清做删除
        CorpDepartmentVO deptVO = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        if (deptVO != null) {
            rsqAccountBizService.deleteRsqDepartment(deptVO);
            // 然后本地做删除
            corpDepartmentManager.deleteCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
        }
    }
}
