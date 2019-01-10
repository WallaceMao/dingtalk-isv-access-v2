package com.rishiqing.dingtalk.converter.suite;

import com.rishiqing.dingtalk.dao.model.suite.AppDO;
import com.rishiqing.dingtalk.isv.api.model.suite.AppVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 20:43
 */
public class AppConverter {
    public static AppVO appDO2AppVO(AppDO appDO){
        if(null==appDO){
            return null;
        }
        AppVO appVO = new AppVO();
        appVO.setId(appDO.getId());
        appVO.setGmtCreate(appDO.getGmtCreate());
        appVO.setGmtModified(appDO.getGmtModified());
        appVO.setAppId(appDO.getAppId());
        appVO.setSuiteKey(appDO.getSuiteKey());
        appVO.setActiveMessage(appDO.getActiveMessage());
        appVO.setAppName(appDO.getAppName());
        appVO.setMainColor(appDO.getMainColor());
        return appVO;
    }

    public static AppDO appVO2AppDO(AppVO appVO){
        if(null==appVO){
            return null;
        }
        AppDO appDO = new AppDO();
        appDO.setId(appVO.getId());
        appDO.setGmtCreate(appVO.getGmtCreate());
        appDO.setGmtModified(appVO.getGmtModified());
        appDO.setAppId(appVO.getAppId());
        appDO.setSuiteKey(appVO.getSuiteKey());
        appDO.setActiveMessage(appVO.getActiveMessage());
        appDO.setAppName(appVO.getAppName());
        appDO.setMainColor(appVO.getMainColor());
        return appDO;
    }
}
