package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.front.PopupInfoVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 14:14
 */
public interface PopupBizService {
    PopupInfoVO getPopupInfo(String corpId, String userId);

    void logStaffPopup(String corpId, String userId, String type);
}
