package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpStaffConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public CorpStaffVO getCorpStaffByCorpIdAndUserId(String corpId, String userId) {
        return CorpStaffConverter.corpStaffDO2CorpStaffVO(
                corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, userId)
        );
    }

    @Override
    public void updateRsqInfo(CorpStaffVO staffVO) {
        corpStaffDao.updateCorpStaffRsqInfo(
                CorpStaffConverter.corpStaffVO2CorpStaffDO(staffVO)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpId(String corpId) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpId(corpId)
        );
    }

    @Override
    public List<CorpStaffVO> getCorpStaffListByCorpIdAndIsAdmin(String corpId, Boolean isAdmin) {
        return CorpStaffConverter.corpStaffDOList2CorpStaffVOList(
                corpStaffDao.getCorpStaffListByCorpIdAndIsAdmin(corpId, isAdmin)
        );
    }
}
