package com.rishiqing.dingtalk.isv.api.service.base.suite;

import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;

/**
 * 套件VO的增删改查
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public interface SuiteManageService {
    /**
     * 获取suite的基本信息
     * @return
     */
    public SuiteVO getSuite();

    /**
     * 获取suite ticket
     * @return
     */
    public SuiteTicketVO getSuiteTicket();

    /**
     * 保存或更新suite ticket
     * @param suiteTicketVO
     */
    public void saveOrUpdateSuiteTicket(SuiteTicketVO suiteTicketVO);

    /**
     * 获取suite token
     * @return
     */
    public SuiteTokenVO getSuiteToken();

    /**
     * 保存或更新suite token
     * @param suiteTokenVO
     */
    public void saveOrUpdateSuiteToken(SuiteTokenVO suiteTokenVO);
}
