package com.rishiqing.dingtalk.biz.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDepartmentDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:59
 */
public class CorpDepartmentConverter {
    public static CorpDepartmentVO corpDepartmentDO2CorpDepartmentVO(CorpDepartmentDO departmentDO){
        if(null==departmentDO){
            return null;
        }
        CorpDepartmentVO departmentVO = new CorpDepartmentVO();
        departmentVO.setDeptId(departmentDO.getDeptId());
        departmentVO.setGmtCreate(departmentDO.getGmtCreate());
        departmentVO.setGmtModified(departmentDO.getGmtModified());
        departmentVO.setCorpId(departmentDO.getCorpId());
        departmentVO.setName(departmentDO.getName());
        departmentVO.setOrder(departmentDO.getOrder());
        departmentVO.setParentId(departmentDO.getParentId());
        departmentVO.setCreateDeptGroup(departmentDO.getCreateDeptGroup());
        departmentVO.setAutoAddUser(departmentDO.getAutoAddUser());
        departmentVO.setDeptHiding(departmentDO.getDeptHiding());
        departmentVO.setDeptPerimits(departmentDO.getDeptPerimits());
        departmentVO.setUserPerimits(departmentDO.getUserPerimits());

        departmentVO.setDeptManagerUseridList(departmentDO.getDeptManagerUseridList());
        departmentVO.setOrgDeptOwner(departmentDO.getOrgDeptOwner());
        departmentVO.setOrder(departmentDO.getOrder());

        departmentVO.setRsqId(departmentDO.getRsqId());
        departmentVO.setScopeVersion(departmentDO.getScopeVersion());

        return departmentVO;
    }

    public static CorpDepartmentDO corpDepartmentVO2CorpDepartmentDO(CorpDepartmentVO departmentVO){
        if(null==departmentVO){
            return null;
        }
        CorpDepartmentDO departmentDO = new CorpDepartmentDO();
        departmentDO.setDeptId(departmentVO.getDeptId());
        departmentDO.setGmtCreate(departmentVO.getGmtCreate());
        departmentDO.setGmtModified(departmentVO.getGmtModified());
        departmentDO.setCorpId(departmentVO.getCorpId());
        departmentDO.setName(departmentVO.getName());
        departmentDO.setOrder(departmentVO.getOrder());
        departmentDO.setParentId(departmentVO.getParentId());
        departmentDO.setCreateDeptGroup(departmentVO.getCreateDeptGroup());
        departmentDO.setAutoAddUser(departmentVO.getAutoAddUser());
        departmentDO.setDeptHiding(departmentVO.getDeptHiding());
        departmentDO.setDeptPerimits(departmentVO.getDeptPerimits());
        departmentDO.setUserPerimits(departmentVO.getUserPerimits());

        departmentDO.setDeptManagerUseridList(departmentVO.getDeptManagerUseridList());
        departmentDO.setOrgDeptOwner(departmentVO.getOrgDeptOwner());
        departmentDO.setOrder(departmentVO.getOrder());

        departmentDO.setRsqId(departmentVO.getRsqId());
        departmentDO.setScopeVersion(departmentVO.getScopeVersion());

        return departmentDO;
    }

    public static List<CorpDepartmentVO> corpDepartmentDOList2CorpDepartmentVOList(List<CorpDepartmentDO> deptList) {
        if(deptList == null){
            return null;
        }
        List<CorpDepartmentVO> voList = new ArrayList<>(deptList.size());
        for(CorpDepartmentDO deptDO : deptList){
            voList.add(corpDepartmentDO2CorpDepartmentVO(deptDO));
        }
        return voList;
    }
}
