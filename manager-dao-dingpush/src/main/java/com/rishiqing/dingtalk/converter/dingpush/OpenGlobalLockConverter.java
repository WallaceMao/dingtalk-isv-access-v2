package com.rishiqing.dingtalk.converter.dingpush;

import com.rishiqing.dingtalk.dao.model.dingpush.OpenGlobalLockDO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;
import org.springframework.expression.spel.ast.OpDec;

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
