package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.dingtalk.mgr.dingmain.constant.ManagerDaoDingmainConstant;
import com.rishiqing.dingtalk.mgr.dingmain.converter.corp.CorpDepartmentConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpDepartmentDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rishiqing.dingtalk.mgr.dingmain.constant.ManagerDaoDingmainConstant.SYS_LIMIT_LIST_DEPARTMENT_MAX;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:35
 */
public class CorpDepartmentManagerImpl implements CorpDepartmentManager {
    @Autowired
    private CorpDepartmentDao corpDepartmentDao;

    @Override
    public CorpDepartmentVO getCorpDepartmentByCorpIdAndDeptId(String corpId, Long deptId){
        return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(
                corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId)
        );
    }

    @Override
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpId(String corpId){
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByCorpId(corpId)
        );
    }

    @Override
    public void saveOrUpdateCorpDepartment(CorpDepartmentVO dept) {
        corpDepartmentDao.saveOrUpdateCorpDepartment(
                CorpDepartmentConverter.corpDepartmentVO2CorpDepartmentDO(dept)
        );
    }

    @Override
    public void updateRsqInfo(CorpDepartmentVO departmentVO) {
        corpDepartmentDao.updateCorpDepartmentRsqInfo(
                CorpDepartmentConverter.corpDepartmentVO2CorpDepartmentDO(departmentVO)
        );
    }

    @Override
    public void deleteCorpDepartmentByCorpIdAndDeptId(String corpId, Long deptId) {
        corpDepartmentDao.deleteCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
    }

    @Override
    public void deleteCorpDepartmentByCorpIdAndScopeVersionLessThan(String corpId, Long scopeVersion) {
        corpDepartmentDao.deleteCorpDepartmentByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
    }

    @Override
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndParentId(String corpId, Long deptId) {
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByParentId(corpId, deptId)
        );
    }

    @Override
    public List<Long> listCorpDepartmentDeptIdByCorpIdAndParentId(String corpId, Long deptId) {
        return corpDepartmentDao.listCorpDepartmentDeptIdByCorpIdAndParentId(corpId, deptId);
    }

    @Override
    public CorpDepartmentVO getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(
            String corpId, Long deptId, Long scopeVersion){
        return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(
                corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(corpId, deptId, scopeVersion)
        );
    }

    @Override
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndScopeVersion(
            String corpId, Long scopeVersion){
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByCorpIdAndScopeVersion(corpId, scopeVersion)
        );
    }

    @Override
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndScopeVersionLessThan(
            String corpId, Long scopeVersion){
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByCorpIdAndScopeVersionLessThan(corpId, scopeVersion)
        );
    }

    @Override
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndParentIdAndScopeVersion(
            String corpId, Long deptId, Long scopeVersion) {
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByParentIdAndScopeVersion(corpId, deptId, scopeVersion)
        );
    }

    /**
     * 获取一个公司中的所有部门中的最顶层的部门，逻辑如下：
     * 1.  如果没有任何部门，那么说明用户在可见范围内没有选择任何部门
     * 2.  如果有id为1的部门，那么说明给用户选择的是全员可见（或者在部分成员可见中选择的是顶层的公司）
     * 3.  如果用户选择的可见范围只是公司的部门属性结构中的一个子集，那么读取出所有parentId为null的部门作为顶级部门
     * @param corpId
     * @param scopeVersion
     * @return
     */
    @Override
    public List<CorpDepartmentVO> listTopCorpDepartmentByScopeVersion(String corpId, Long scopeVersion){
        List<CorpDepartmentDO> deptDOList = corpDepartmentDao.getCorpDepartmentListByCorpIdAndScopeVersionLimit(
                corpId, scopeVersion, 1L);
        //  如果找不到部门，说明该公司没有部门，那么顶层部门为空
        if(deptDOList == null || deptDOList.size() == 0){
            return null;
        }
        List<CorpDepartmentVO> voList = new ArrayList<>();
        CorpDepartmentDO rootDept = corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(
                corpId, ManagerDaoDingmainConstant.DEPT_ID_ROOT, scopeVersion);
        //  根据钉钉的约定，公司的根部门的id为1，那么如果能找到根部门，那么直接将根部门作为顶层部门返回
        if(rootDept != null){
            voList.add(CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(rootDept));
            return voList;
        }
        //  最复杂的情况，如果用户选择的可见范围只是公司的部门属性结构中的一个子集，那么读取出所有parentId为null的部门作为顶级部门
        deptDOList = corpDepartmentDao.getCorpDepartmentListByCorpIdAndScopeVersionLimit(
                corpId, scopeVersion, SYS_LIMIT_LIST_DEPARTMENT_MAX);
        //  以deptId作为key值，保存在deptIdMap里
        Map<Long, CorpDepartmentDO> deptIdMap = new HashMap<>(deptDOList.size());
        for (CorpDepartmentDO deptDO : deptDOList) {
            deptIdMap.put(deptDO.getDeptId(), deptDO);
        }
        //  便利deptIdMap，如果一个dept的parentId不在deptId中，那么说明这个dept就添加到顶级部门列表
        for (Map.Entry<Long, CorpDepartmentDO> entry : deptIdMap.entrySet()) {
            CorpDepartmentDO deptDO = entry.getValue();
            if (deptIdMap.get(deptDO.getParentId()) == null) {
                voList.add(CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(deptDO));
            }
        }
        return voList;
    }
}
