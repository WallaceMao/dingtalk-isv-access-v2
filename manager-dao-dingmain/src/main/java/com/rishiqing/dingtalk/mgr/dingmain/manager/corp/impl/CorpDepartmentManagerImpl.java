package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.corp.CorpDepartmentConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpDepartmentDO;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
     * 获取一个公司中的所有部门中的最顶层的部门
     * @param corpId
     * @param scopeVersion
     * @return
     */
    @Override
    public CorpDepartmentVO getTopCorpDepartmentByScopeVersion(String corpId, Long scopeVersion){
        List<CorpDepartmentDO> deptDOList = corpDepartmentDao.getCorpDepartmentListByCorpIdAndScopeVersionLimit(
                corpId, scopeVersion, 1L);
        //  如果找不到部门，说明该公司没有部门，那么顶层部门为空
        if(deptDOList == null || deptDOList.size() == 0){
            return null;
        }
        CorpDepartmentDO rootDept = corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(
                corpId, 1L, scopeVersion);
        //  根据钉钉的约定，公司的根部门的id为1，那么如果能找到根部门，那么直接将根部门作为顶层部门返回
        if(rootDept != null){
            return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(rootDept);
        }
        //  最复杂的情况，如果用户选择的可见范围只是公司的部门属性结构中的一个子集，那么就只能从一个部门开始迭代查找
        CorpDepartmentDO currentDept = deptDOList.get(0);
        final int LOOP_LIMIT = 50;  //  对循环做限制
        int i = 0;
        //  什么情况下currentDept就是topDepartment呢？
        //  1  currentDept.getParentId()为null。
        //  2  currentDept的parentId存在，但是获取不到值
        while (true){
            //  如果parentId为空，那么跳出循环，currentDept就是topDept
            if(currentDept.getParentId() == null){
                break;
            }
            CorpDepartmentDO parentDept  = corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(
                    corpId, currentDept.getParentId(), scopeVersion);
            //  如果parentDept为空，那么跳出循环，currentDpet就是topDept
            if(parentDept == null){
                break;
            }
            currentDept = parentDept;
            //  如果达到循环上限，那么抛出异常
            if(i++ >= LOOP_LIMIT){
                throw new BizRuntimeException("getTopCorpDepartment reach max loop limit, corpId: " + corpId + ", loop times: " + i);
            }
        }
        return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(currentDept);
    }
}
