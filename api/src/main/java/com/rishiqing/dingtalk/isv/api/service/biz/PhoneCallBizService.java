package com.rishiqing.dingtalk.isv.api.service.biz;

/**
 * @author Wallace Mao
 * Date: 2018-12-13 18:00
 */
public interface PhoneCallBizService {
    void callActivateAdmin(String corpId, String loginUserId);

    void setCalleeList(String calleeList);

    void removeCalleeList(String calleeList);
}
