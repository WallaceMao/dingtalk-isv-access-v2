package com.rishiqing.dingtalk.biz.converter.dingpush;

import com.rishiqing.dingtalk.dao.model.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:50
 */
public class OpenSyncBizDataConverter {

    public static List<OpenSyncBizDataVO> openSyncBizDataDOList2openSyncBizDataVOList(List<OpenSyncBizDataDO> doList){
        if(doList == null){
            return null;
        }
        List<OpenSyncBizDataVO> voList = new ArrayList<>(doList.size());
        for(OpenSyncBizDataDO obj : doList){
            voList.add(
                    openSyncBizDataDO2openSyncBizDataVO(obj)
            );
        }
        return voList;
    }
    public static OpenSyncBizDataVO openSyncBizDataDO2openSyncBizDataVO(OpenSyncBizDataDO openSyncBizDataDO){
        if(openSyncBizDataDO == null){
            return null;
        }
        OpenSyncBizDataVO openSyncBizDataVO = new OpenSyncBizDataVO();
        openSyncBizDataVO.setId(openSyncBizDataDO.getId());
        openSyncBizDataVO.setGmtCreate(openSyncBizDataDO.getGmtCreate());
        openSyncBizDataVO.setGmtModified(openSyncBizDataDO.getGmtModified());
        openSyncBizDataVO.setSubscribeId(openSyncBizDataDO.getSubscribeId());
        openSyncBizDataVO.setCorpId(openSyncBizDataDO.getCorpId());
        openSyncBizDataVO.setBizType(openSyncBizDataDO.getBizType());
        openSyncBizDataVO.setBizId(openSyncBizDataDO.getBizId());
        openSyncBizDataVO.setBizData(openSyncBizDataDO.getBizData());
        openSyncBizDataVO.setOpenCursor(openSyncBizDataDO.getOpenCursor());
        openSyncBizDataVO.setStatus(openSyncBizDataDO.getStatus());
        return openSyncBizDataVO;
    }

    public static OpenSyncBizDataDO openSyncBizDataVO2openSyncBizDataDO(OpenSyncBizDataVO openSyncBizDataVO){
        if(openSyncBizDataVO == null){
            return null;
        }
        OpenSyncBizDataDO openSyncBizDataDO = new OpenSyncBizDataDO();
        openSyncBizDataDO.setId(openSyncBizDataVO.getId());
        openSyncBizDataDO.setGmtCreate(openSyncBizDataVO.getGmtCreate());
        openSyncBizDataDO.setGmtModified(openSyncBizDataVO.getGmtModified());
        openSyncBizDataDO.setSubscribeId(openSyncBizDataVO.getSubscribeId());
        openSyncBizDataDO.setCorpId(openSyncBizDataVO.getCorpId());
        openSyncBizDataDO.setBizType(openSyncBizDataVO.getBizType());
        openSyncBizDataDO.setBizId(openSyncBizDataVO.getBizId());
        openSyncBizDataDO.setBizData(openSyncBizDataVO.getBizData());
        openSyncBizDataDO.setOpenCursor(openSyncBizDataVO.getOpenCursor());
        openSyncBizDataDO.setStatus(openSyncBizDataVO.getStatus());
        return openSyncBizDataDO;
    }
}
