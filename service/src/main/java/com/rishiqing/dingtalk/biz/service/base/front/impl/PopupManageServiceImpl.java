package com.rishiqing.dingtalk.biz.service.base.front.impl;

import com.rishiqing.dingtalk.biz.converter.front.PopupConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpChargeStatusDao;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpStaffDao;
import com.rishiqing.dingtalk.dao.mapper.front.StaffPopupConfigDao;
import com.rishiqing.dingtalk.dao.mapper.front.StaffPopupLogDao;
import com.rishiqing.dingtalk.dao.mapper.order.OrderSpecItemDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpChargeStatusDO;
import com.rishiqing.dingtalk.dao.model.corp.CorpStaffDO;
import com.rishiqing.dingtalk.dao.model.front.StaffPopupConfigDO;
import com.rishiqing.dingtalk.dao.model.front.StaffPopupLogDO;
import com.rishiqing.dingtalk.dao.model.order.OrderSpecItemDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;
import com.rishiqing.dingtalk.isv.api.service.base.front.PopupManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:43
 */
public class PopupManageServiceImpl implements PopupManageService {
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
