package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.manager.corp.CorpManager;
import com.rishiqing.dingtalk.manager.corp.CorpStaffManager;
import com.rishiqing.dingtalk.manager.front.PopupManager;
import com.rishiqing.dingtalk.manager.order.OrderManager;
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
    private CorpManager corpManager;
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private PopupManager popupManager;

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
        CorpStaffVO staffVO = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);
        popupInfo.setAdmin(staffVO.getAdmin());

        //  读取企业信息
        CorpChargeStatusVO corpStatus = corpManager.getCorpChargeStatusByCorpId(corpId);
        if(corpStatus != null){
            popupInfo.setServiceExpire(corpStatus.getCurrentServiceStopTime());
            Long buyNumber = corpStatus.getCurrentSubQuantity() != null ? corpStatus.getCurrentSubQuantity() : corpStatus.getCurrentMaxOfPeople();
            popupInfo.setBuyNumber(buyNumber);

            //TODO 暂时使用count来数团队的人数，以后遇到性能问题，需要从corpStatistic中读取公司的用户数量
            Long staffCount = corpStaffManager.countCorpStaffByCorpId(corpId);
            popupInfo.setTotalNumber(staffCount);
            // CorpStatisticVO corpStatistic = corpManager.getCorpStatisticByCorpId(corpId);
            // if(corpStatistic != null) {
            //     popupInfo.setTotalNumber(corpStatistic.getStaffCount());
            // }

            //  读取规格信息
            OrderSpecItemVO spec = orderManager.getOrderSpecItemByGoodsCodeAndItemCode(
                    corpStatus.getCurrentGoodsCode(),
                    corpStatus.getCurrentItemCode());

            popupInfo.setSpecKey(spec.getInnerKey());
        }

        //  读取mute信息
        List<StaffPopupConfigVO> configList =
                popupManager.getStaffPopupConfigListByIsDisabled(false);
        Map<String, StaffPopupConfigVO> popupConfigMap = new HashMap<>();
        Map<String, StaffPopupLogVO> muteInfoMap = new HashMap<>();
        for(StaffPopupConfigVO configVO : configList){
            String type = configVO.getPopupType();
            popupConfigMap.put(type, configVO);
            StaffPopupLogVO logVO =
                    popupManager.getStaffPopupLogByCorpIdAndUserId(corpId, userId, type);
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
