package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpStaffConverter;
import com.rishiqing.dingtalk.biz.util.RegExpUtil;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpStaffDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:35
 */
public class CorpStaffManageServiceImpl implements CorpStaffManageService {
    @Autowired
    private CorpStaffDao corpStaffDao;

    @Override
    public void saveOrUpdateCorpStaff(CorpStaffVO staff) {
        corpStaffDao.saveOrUpdateCorpStaff(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(staff)
        );
    }

    @Override
    public void updateRsqInfo(CorpStaffVO staffVO) {
        corpStaffDao.updateCorpStaffRsqInfo(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(staffVO)
        );
    }

    @Override
    public void deleteCorpStaffByCorpIdAndUserId(String corpId, String userId) {
        corpStaffDao.deleteCorpStaffByCorpIdAndUserId(corpId, userId);
    }

    @Override
    public void deleteCorpStaffByCorpIdAndScopeVersionLessThan(String corpId, Long lessThanScopeVersion) {
        corpStaffDao.deleteCorpStaffByCorpIdAndScopeVersionLessThan(corpId, lessThanScopeVersion);
    }

    @Override
    public CorpStaffVO getCorpStaffByCorpIdAndUserId(String corpId, String userId) {
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(
                corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, userId)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpId(String corpId) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpId(corpId)
        );
    }

    @Override
    public List<String> getCorpStaffUserIdListByCorpId(String corpId) {
        return corpStaffDao.getCorpStaffUserIdListByCorpId(corpId);
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpIdAndIsAdmin(String corpId, Boolean isAdmin) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpIdAndIsAdmin(corpId, isAdmin)
        );
    }

    @Override
    public CorpStaffVO getCorpStaffByCorpIdAndUserIdAndScopeVersion(
            String corpId, String userId, Long scopeVersion) {
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(
                corpStaffDao.getCorpStaffByCorpIdAndUserIdAndScopeVersion(corpId, userId, scopeVersion)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpIdAndScopeVersion(
            String corpId, Long scopeVersion) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpIdAndScopeVersion(corpId, scopeVersion)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpIdAndScopeVersionLessThan(
            String corpId, Long scopeVersion) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpIdAndScopeVersionLessThan(corpId, scopeVersion)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpIdAndIsAdminAndScopeVersion(
            String corpId, Boolean isAdmin, Long scopeVersion) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpIdAndIsAdminAndScopeVersion(corpId, isAdmin, scopeVersion)
        );
    }

    @Override
    public List<CorpStaffVO> listCorpStaffByCorpIdAndDepartment(String corpId, Long deptId) {
        List<CorpStaffDO> doList = corpStaffDao.listCorpStaffByCorpIdAndDepartmentLike(corpId, "%" + deptId + "%");
        List<CorpStaffVO> voList = new ArrayList<>(doList.size());
        //  从doList读出的corpStaffDO并不一定是deptId中的，因为CorpStaffDO中存储所在部门使用的是String类型
        // 例如，如果一个CorpStaffDO的department字段为："[1234,567]"，而需要查找的deptId为234，那么该CorpStaffDO也会被查出来
        // 这里处于对数据库性能的考虑，不在数据库层面处理字符串问题，而是在应用层面对模糊匹配的结果再做一遍过滤
        for (CorpStaffDO corpStaffDO : doList) {
            String departmentString = corpStaffDO.getDepartment();
            if (departmentString == null) {
                continue;
            }
            if (RegExpUtil.ArrayStringContainsElement(departmentString, String.valueOf(deptId))) {
                voList.add(CorpStaffConverter.corpStaffDO2CorpStaffVO(corpStaffDO));
            }
        }
        return voList;
    }

    @Override
    public Long countCorpStaffByCorpId(String corpId) {
        return corpStaffDao.countCorpStaffByCorpId(corpId);
    }

}
