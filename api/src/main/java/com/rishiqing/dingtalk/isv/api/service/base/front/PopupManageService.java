package com.rishiqing.dingtalk.isv.api.service.base.front;

import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupConfigVO;
import com.rishiqing.dingtalk.isv.api.model.front.StaffPopupLogVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:42
 */
public interface PopupManageService {
    List<StaffPopupConfigVO> getStaffPopupConfigListByIsDisabled(Boolean isDisabled);

    StaffPopupLogVO getStaffPopupLogByCorpIdAndUserId(String corpId, String userId, String popupType);
}
