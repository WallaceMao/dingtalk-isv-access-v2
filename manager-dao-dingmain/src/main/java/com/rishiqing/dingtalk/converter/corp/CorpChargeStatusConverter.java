package com.rishiqing.dingtalk.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpChargeStatusDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;

/**
 * @author Wallace Mao
 * Date: 2018-12-24 14:41
 */
public class CorpChargeStatusConverter {
    public static CorpChargeStatusVO corpChargeStatusDO2CorpChargeStatusVO(CorpChargeStatusDO corpChargeStatusDO){
        if(corpChargeStatusDO == null){
            return null;
        }
        CorpChargeStatusVO statusVO = new CorpChargeStatusVO();
        statusVO.setId(corpChargeStatusDO.getId());
        statusVO.setGmtCreate(corpChargeStatusDO.getGmtCreate());
        statusVO.setGmtModified(corpChargeStatusDO.getGmtModified());
        statusVO.setSuiteKey(corpChargeStatusDO.getSuiteKey());
        statusVO.setCorpId(corpChargeStatusDO.getCorpId());
        statusVO.setTotalQuantity(corpChargeStatusDO.getTotalQuantity());
        statusVO.setCurrentOrderId(corpChargeStatusDO.getCurrentOrderId());
        statusVO.setCurrentGoodsCode(corpChargeStatusDO.getCurrentGoodsCode());
        statusVO.setCurrentItemCode(corpChargeStatusDO.getCurrentItemCode());
        statusVO.setCurrentSubQuantity(corpChargeStatusDO.getCurrentSubQuantity());
        statusVO.setCurrentMaxOfPeople(corpChargeStatusDO.getCurrentMaxOfPeople());
        statusVO.setCurrentMinOfPeople(corpChargeStatusDO.getCurrentMinOfPeople());
        statusVO.setCurrentServiceStopTime(corpChargeStatusDO.getCurrentServiceStopTime());
        statusVO.setStatus(corpChargeStatusDO.getStatus());
        return statusVO;
    }

    public static CorpChargeStatusDO corpChargeStatusVO2CorpChargeStatusDO(CorpChargeStatusVO statusVO){
        if(statusVO == null){
            return null;
        }
        CorpChargeStatusDO statusDO = new CorpChargeStatusDO();
        statusDO.setId(statusVO.getId());
        statusDO.setGmtCreate(statusVO.getGmtCreate());
        statusDO.setGmtModified(statusVO.getGmtModified());
        statusDO.setSuiteKey(statusVO.getSuiteKey());
        statusDO.setCorpId(statusVO.getCorpId());
        statusDO.setTotalQuantity(statusVO.getTotalQuantity());
        statusDO.setCurrentOrderId(statusVO.getCurrentOrderId());
        statusDO.setCurrentGoodsCode(statusVO.getCurrentGoodsCode());
        statusDO.setCurrentItemCode(statusVO.getCurrentItemCode());
        statusDO.setCurrentSubQuantity(statusVO.getCurrentSubQuantity());
        statusDO.setCurrentMaxOfPeople(statusVO.getCurrentMaxOfPeople());
        statusDO.setCurrentMinOfPeople(statusVO.getCurrentMinOfPeople());
        statusDO.setCurrentServiceStopTime(statusVO.getCurrentServiceStopTime());
        statusDO.setStatus(statusVO.getStatus());

        return statusDO;
    }
}
