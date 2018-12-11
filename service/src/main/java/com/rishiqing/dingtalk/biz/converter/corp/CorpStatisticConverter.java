package com.rishiqing.dingtalk.biz.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpStatisticDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStatisticVO;

/**
 * @author Wallace Mao
 * Date: 2018-12-11 19:11
 */
public class CorpStatisticConverter {
    public static CorpStatisticDO corpStatisticVO2CorpStatisticDO(CorpStatisticVO corpStatisticVO) {
        if (corpStatisticVO == null) {
            return null;
        }
        CorpStatisticDO corpStatisticDO = new CorpStatisticDO();
        corpStatisticDO.setId(corpStatisticVO.getId());
        corpStatisticDO.setGmtCreate(corpStatisticVO.getGmtCreate());
        corpStatisticDO.setGmtModified(corpStatisticVO.getGmtModified());
        corpStatisticDO.setCorpId(corpStatisticVO.getCorpId());
        corpStatisticDO.setStaffCount(corpStatisticVO.getStaffCount());
        return corpStatisticDO;
    }

    public static CorpStatisticVO corpStatisticDO2CorpStatisticVO(CorpStatisticDO corpStatisticDO) {
        if (corpStatisticDO == null) {
            return null;
        }
        CorpStatisticVO corpStatisticVO = new CorpStatisticVO();
        corpStatisticVO.setId(corpStatisticDO.getId());
        corpStatisticVO.setGmtCreate(corpStatisticDO.getGmtCreate());
        corpStatisticVO.setGmtModified(corpStatisticDO.getGmtModified());
        corpStatisticVO.setCorpId(corpStatisticDO.getCorpId());
        corpStatisticVO.setStaffCount(corpStatisticDO.getStaffCount());
        return corpStatisticVO;
    }
}
