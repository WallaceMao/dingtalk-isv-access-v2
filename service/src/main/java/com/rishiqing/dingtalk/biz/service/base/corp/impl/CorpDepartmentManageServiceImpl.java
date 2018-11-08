package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpDepartmentConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpDepartmentDO;
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
        CorpDepartmentDO deptDO = corpDepartmentDao.get
    }
}
