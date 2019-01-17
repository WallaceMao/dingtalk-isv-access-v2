package com.rishiqing.dingtalk.mgr.dingmain.converter.front;

import com.rishiqing.dingtalk.api.model.domain.front.StaffPopupConfigDO;
import com.rishiqing.dingtalk.api.model.domain.front.StaffPopupLogDO;
import com.rishiqing.dingtalk.api.model.vo.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.api.model.vo.front.StaffPopupLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:41
 */
public class PopupConverter {
    public static StaffPopupConfigVO staffPopupConfigDO2StaffPopupConfigVO(StaffPopupConfigDO configDO) {
        if(configDO == null){
            return null;
        }
        StaffPopupConfigVO configVO = new StaffPopupConfigVO();
        configVO.setId(configDO.getId());
        configVO.setGmtCreate(configDO.getGmtCreate());
        configVO.setGmtModified(configDO.getGmtModified());
        configVO.setSuiteKey(configDO.getSuiteKey());
        configVO.setPopupType(configDO.getPopupType());
        configVO.setDisabled(configDO.getDisabled());
        configVO.setSalePhoneNumber(configDO.getSalePhoneNumber());
        configVO.setSaleQrCodeUrl(configDO.getSaleQrCodeUrl());
        return configVO;
    }

    public static StaffPopupLogVO staffPopupLogDO2StaffPopupLogVO(StaffPopupLogDO logDO) {
        if(logDO == null){
            return null;
        }
        StaffPopupLogVO logVO = new StaffPopupLogVO();
        logVO.setId(logDO.getId());
        logVO.setGmtCreate(logDO.getGmtCreate());
        logVO.setGmtModified(logDO.getGmtModified());
        logVO.setSuiteKey(logDO.getSuiteKey());
        logVO.setCorpId(logDO.getCorpId());
        logVO.setPopupType(logDO.getPopupType());
        logVO.setUserId(logDO.getUserId());
        logVO.setPopupMuteExpire(logDO.getPopupMuteExpire());
        return logVO;
    }

    public static List<StaffPopupConfigVO> staffPopupConfigDOList2StaffPopupConfigVOList(List<StaffPopupConfigDO> doList) {
        if(doList == null){
            return null;
        }
        List<StaffPopupConfigVO> voList = new ArrayList<>(doList.size());
        for(StaffPopupConfigDO configDO : doList){
            voList.add(staffPopupConfigDO2StaffPopupConfigVO(configDO));
        }
        return voList;
    }
}
