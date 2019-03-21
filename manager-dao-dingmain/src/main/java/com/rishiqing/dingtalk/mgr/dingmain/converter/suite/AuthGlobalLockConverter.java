package com.rishiqing.dingtalk.mgr.dingmain.converter.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.AuthGlobalLockDO;
import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 23:42
 */
public class AuthGlobalLockConverter {
    public static AuthGlobalLockVO authGlobalLockDO2authGlobalLockVO(AuthGlobalLockDO authGlobalLockDO){
        if(authGlobalLockDO == null){
            return null;
        }
        AuthGlobalLockVO authGlobalLockVO = new AuthGlobalLockVO();
        authGlobalLockVO.setId(authGlobalLockDO.getId());
        authGlobalLockVO.setLockKey(authGlobalLockDO.getLockKey());
        authGlobalLockVO.setStatus(authGlobalLockDO.getStatus());
        return authGlobalLockVO;
    }

    public static AuthGlobalLockDO authGlobalLockVO2authGlobalLockDO(AuthGlobalLockVO authGlobalLockVO){
        if(authGlobalLockVO == null){
            return null;
        }
        AuthGlobalLockDO authGlobalLockDO = new AuthGlobalLockDO();
        authGlobalLockDO.setId(authGlobalLockVO.getId());
        authGlobalLockDO.setLockKey(authGlobalLockVO.getLockKey());
        authGlobalLockDO.setStatus(authGlobalLockVO.getStatus());
        return authGlobalLockDO;
    }
}
