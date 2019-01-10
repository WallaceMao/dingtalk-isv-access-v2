package com.rishiqing.dingtalk.manager.front.impl;

import com.rishiqing.dingtalk.converter.front.PopupConverter;
import com.rishiqing.dingtalk.dao.mapper.front.StaffPopupConfigDao;
import com.rishiqing.dingtalk.dao.mapper.front.StaffPopupLogDao;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;
import com.rishiqing.dingtalk.manager.front.PopupManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:43
 */
public class PopupManagerImpl implements PopupManager {
    @Autowired
    private StaffPopupConfigDao staffPopupConfigDao;
    @Autowired
    private StaffPopupLogDao staffPopupLogDao;

    @Override
    public List<StaffPopupConfigVO> getStaffPopupConfigListByIsDisabled(Boolean isDisabled){
        return PopupConverter.staffPopupConfigDOList2StaffPopupConfigVOList(
                staffPopupConfigDao.getStaffPopupConfigListByIsDisabled(isDisabled)
        );
    }

    @Override
    public StaffPopupLogVO getStaffPopupLogByCorpIdAndUserId(String corpId, String userId, String popupType){
        return PopupConverter.staffPopupLogDO2StaffPopupLogVO(
                staffPopupLogDao.getStaffPopupLogByCorpIdAndUserIdAndPopupType(corpId, userId, popupType)
        );
    }
}
