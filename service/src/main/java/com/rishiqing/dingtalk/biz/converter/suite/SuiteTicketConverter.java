package com.rishiqing.dingtalk.biz.converter.suite;

import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.dao.model.suite.SuiteTicketDO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 17:17
 */
public class SuiteTicketConverter {
    public static SuiteTicketVO suiteTicketDO2SuiteTicketVO(SuiteTicketDO suiteTicketDO){
        if(null==suiteTicketDO){
            return null;
        }
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setId(suiteTicketDO.getId());
        suiteTicketVO.setGmtCreate(suiteTicketDO.getGmtCreate());
        suiteTicketVO.setGmtModified(suiteTicketDO.getGmtModified());
        suiteTicketVO.setSuiteKey(suiteTicketDO.getSuiteKey());
        suiteTicketVO.setSuiteTicket(suiteTicketDO.getSuiteTicket());
        return suiteTicketVO;
    }


    public static SuiteTicketDO suiteTicketVO2SuiteTicketDO(SuiteTicketVO suiteTicketVO){
        if(null==suiteTicketVO){
            return null;
        }
        SuiteTicketDO suiteTicketDO = new SuiteTicketDO();
        suiteTicketDO.setId(suiteTicketVO.getId());
        suiteTicketDO.setGmtCreate(suiteTicketVO.getGmtCreate());
        suiteTicketDO.setGmtModified(suiteTicketVO.getGmtModified());
        suiteTicketDO.setSuiteKey(suiteTicketVO.getSuiteKey());
        suiteTicketDO.setSuiteTicket(suiteTicketVO.getSuiteTicket());
        return suiteTicketDO;
    }
}
