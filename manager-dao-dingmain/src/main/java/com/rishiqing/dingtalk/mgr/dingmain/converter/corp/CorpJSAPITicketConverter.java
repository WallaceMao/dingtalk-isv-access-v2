package com.rishiqing.dingtalk.mgr.dingmain.converter.corp;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpJSAPITicketDO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 19:09
 */
public class CorpJSAPITicketConverter {
    public static CorpJSAPITicketVO corpJSTicketDO2CorpJSTicketVO(CorpJSAPITicketDO corpJSTicketDO){
        if(null==corpJSTicketDO){
            return null;
        }
        CorpJSAPITicketVO corpJSTicketVO = new CorpJSAPITicketVO();
        corpJSTicketVO.setId(corpJSTicketDO.getId());
        corpJSTicketVO.setGmtCreate(corpJSTicketDO.getGmtCreate());
        corpJSTicketVO.setGmtModified(corpJSTicketDO.getGmtModified());
        corpJSTicketVO.setCorpId(corpJSTicketDO.getCorpId());
        corpJSTicketVO.setSuiteKey(corpJSTicketDO.getSuiteKey());
        corpJSTicketVO.setCorpJSAPITicket(corpJSTicketDO.getCorpJSAPITicket());
        corpJSTicketVO.setExpiredTime(corpJSTicketDO.getExpiredTime());
        return corpJSTicketVO;
    }

    public static CorpJSAPITicketDO corpJSTicketVO2CorpJSTicketDO(CorpJSAPITicketVO corpJSTicketVO){
        if(null==corpJSTicketVO){
            return null;
        }
        CorpJSAPITicketDO corpJSTicketDO = new CorpJSAPITicketDO();
        corpJSTicketDO.setId(corpJSTicketVO.getId());
        corpJSTicketDO.setGmtCreate(corpJSTicketVO.getGmtCreate());
        corpJSTicketDO.setGmtModified(corpJSTicketVO.getGmtModified());
        corpJSTicketDO.setCorpId(corpJSTicketVO.getCorpId());
        corpJSTicketDO.setSuiteKey(corpJSTicketVO.getSuiteKey());
        corpJSTicketDO.setCorpJSAPITicket(corpJSTicketVO.getCorpJSAPITicket());
        corpJSTicketDO.setExpiredTime(corpJSTicketVO.getExpiredTime());
        return corpJSTicketDO;
    }
}
