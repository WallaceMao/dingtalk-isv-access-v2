package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.base.front.PopupManageService;
import com.rishiqing.dingtalk.isv.api.service.base.order.OrderManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.PopupBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 14:08
 */
public class PopupBizServiceImpl implements PopupBizService {
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private PopupManageService popupManageService;

    @Override
    public PopupInfoVO getPopupInfo(String corpId, String userId){
        PopupInfoVO popupInfo = new PopupInfoVO();
        //  设置默认的数据
        popupInfo.setCorpId(corpId);
        popupInfo.setServiceExpire(0L);
        popupInfo.setBuyNumber(0L);
        popupInfo.setTotalNumber(0L);
        popupInfo.setSpecKey("");
        //  读取当前用户信息
        CorpStaffVO staffVO = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        popupInfo.setAdmin(staffVO.getAdmin());

        //  读取企业信息
        CorpChargeStatusVO corpStatus = corpManageService.getCorpChargeStatusByCorpId(corpId);
        if(corpStatus != null){
            popupInfo.setServiceExpire(corpStatus.getCurrentServiceStopTime());
            Long buyNumber = corpStatus.getCurrentSubQuantity() != null ? corpStatus.getCurrentSubQuantity() : corpStatus.getCurrentMaxOfPeople();
            popupInfo.setBuyNumber(buyNumber);
            popupInfo.setTotalNumber(corpStatus.getTotalQuantity());

            //  读取规格信息
            OrderSpecItemVO spec = orderManageService.getOrderSpecItemByGoodsCodeAndItemCode(
                    corpStatus.getCurrentGoodsCode(),
                    corpStatus.getCurrentItemCode());

            popupInfo.setSpecKey(spec.getInnerKey());
        }

        //  读取mute信息
        List<StaffPopupConfigVO> configList =
                popupManageService.getStaffPopupConfigListByIsDisabled(false);
        Map<String, StaffPopupConfigVO> popupConfigMap = new HashMap<>();
        Map<String, StaffPopupLogVO> muteInfoMap = new HashMap<>();
        for(StaffPopupConfigVO configVO : configList){
            String type = configVO.getPopupType();
            popupConfigMap.put(type, configVO);
            StaffPopupLogVO logVO =
                    popupManageService.getStaffPopupLogByCorpIdAndUserId(corpId, userId, type);
            muteInfoMap.put(type, logVO);
        }
        popupInfo.setPopupConfigMap(popupConfigMap);
        popupInfo.setMuteInfoMap(muteInfoMap);

        return popupInfo;
    }

    @Override
    public void logStaffPopup(String corpId, String userId, String type) {

    }
}
