package com.rishiqing.dingtalk.mgr.dingpush.converter;

import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenGlobalLockDO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 23:42
 */
public class OpenGlobalLockConverter {
    public static OpenGlobalLockVO openGlobalLockDO2openGlobalLockVO(OpenGlobalLockDO openGlobalLockDO){
        if(openGlobalLockDO == null){
            return null;
        }
        OpenGlobalLockVO openGlobalLockVO = new OpenGlobalLockVO();
        openGlobalLockVO.setId(openGlobalLockDO.getId());
        openGlobalLockVO.setLockKey(openGlobalLockDO.getLockKey());
        openGlobalLockVO.setStatus(openGlobalLockDO.getStatus());
        return openGlobalLockVO;
    }

    public static OpenGlobalLockDO openGlobalLockVO2openGlobalLockDO(OpenGlobalLockVO openGlobalLockVO){
        if(openGlobalLockVO == null){
            return null;
        }
        OpenGlobalLockDO openGlobalLockDO = new OpenGlobalLockDO();
        openGlobalLockDO.setId(openGlobalLockVO.getId());
        openGlobalLockDO.setLockKey(openGlobalLockVO.getLockKey());
        openGlobalLockDO.setStatus(openGlobalLockVO.getStatus());
        return openGlobalLockDO;
    }
}
