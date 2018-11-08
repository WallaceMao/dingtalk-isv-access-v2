package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpDepartmentConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpDepartmentDO;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:35
 */
public class CorpDepartmentManageServiceImpl implements CorpDepartmentManageService {
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
    public List<CorpDepartmentVO> getCorpDepartmentListByCorpIdAndParentId(String corpId, Long deptId) {
        return CorpDepartmentConverter.corpDepartmentDOList2CorpDepartmentVOList(
                corpDepartmentDao.getCorpDepartmentListByParentId(corpId, deptId)
        );
    }

    /**
     * 获取一个公司中的所有部门中的最顶层的部门
     * @param corpId
     * @return
     */
    @Override
    public CorpDepartmentVO getTopCorpDepartment(String corpId){
        List<CorpDepartmentDO> deptDOList = corpDepartmentDao.getCorpDepartmentListByCorpIdLimit(corpId, 1L);
        //  如果找不到部门，说明该公司没有部门，那么顶层部门为空
        if(deptDOList == null || deptDOList.size() == 0){
            return null;
        }
        CorpDepartmentDO rootDept = corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptId(corpId, 1L);
        //  根据钉钉的约定，公司的根部门的id为1，那么如果能找到根部门，那么直接将根部门作为顶层部门返回
        if(rootDept != null){
            return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(rootDept);
        }
        //  最复杂的情况，如果用户选择的可见范围只是公司的部门属性结构中的一个子集，那么就只能从一个部门开始迭代查找
        CorpDepartmentDO currentDept = deptDOList.get(0);
        final int LOOP_LIMIT = 50;  //  对循环做限制
        int i = 0;
        while (currentDept.getParentId() != null){
            currentDept = corpDepartmentDao.getCorpDepartmentByCorpIdAndDeptId(corpId, currentDept.getParentId());
            //  如果达到循环上限，那么抛出异常
            if(i++ >= LOOP_LIMIT){
                throw new BizRuntimeException("getTopCorpDepartment reach max loop limit, corpId: " + corpId + ", loop times: " + i);
            }
        }
        return CorpDepartmentConverter.corpDepartmentDO2CorpDepartmentVO(currentDept);
    }
}
