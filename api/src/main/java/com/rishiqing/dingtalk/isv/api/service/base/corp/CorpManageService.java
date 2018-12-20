package com.rishiqing.dingtalk.isv.api.service.base.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.isv.api.model.corp.*;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:23
 */
public interface CorpManageService {
    void saveOrUpdateCorp(CorpVO corpVO);

    void saveOrUpdateCorpJSTicket(CorpJSAPITicketVO corpJSAPITicketVO);

    CorpDO getCorpById(Long id);

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

    void saveOrUpdateCorpStatisticUserCount(String corpId, Long userCount);

    void saveOrUpdateCorpStatistic(CorpStatisticVO corpStatisticVO);

    CorpStatisticVO getCorpStatisticByCorpId(String corpId);

    CorpStatisticVO getCorpStatisticByCorpIdForUpdate(String corpId);

    List<CorpDO> listPageCorpWithCreator(Long pageSize, Long offset);

    Long countCorp();

    Long countCorpSuiteAuth();
}
