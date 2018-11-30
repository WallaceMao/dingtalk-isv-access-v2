package com.rishiqing.dingtalk.isv.api.service.base.corp;

import com.rishiqing.dingtalk.isv.api.model.corp.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:23
 */
public interface CorpManageService {
    void saveOrUpdateCorp(CorpVO corpVO);

    void saveOrUpdateCorpJSTicket(CorpJSAPITicketVO corpJSAPITicketVO);

    CorpVO getCorpByCorpId(String corpId);

    void updateRsqInfo(CorpVO corpVO);

    CorpTokenVO getCorpTokenByCorpId(String corpId);

    CorpJSAPITicketVO getCorpJSAPITicketByCorpId(String corpId);

    CorpChargeStatusVO getCorpChargeStatusByCorpId(String corpId);

    CorpStaffVO findATeamCreator(String corpId);

    void deleteCorpSuiteAuthByCorpId(String corpId);

    void deleteCorpAppByCorpId(String corpId, Long appId);

    void deleteCorpTokenByCorpId(String corpId);

    void deleteCorpJSAPITicketByCorpId(String corpId);
}
