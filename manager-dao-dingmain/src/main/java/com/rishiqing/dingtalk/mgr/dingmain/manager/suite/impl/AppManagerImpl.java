package com.rishiqing.dingtalk.mgr.dingmain.manager.suite.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.suite.AppConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.AppDao;
import com.rishiqing.dingtalk.api.model.vo.suite.AppVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.AppManager;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Wallace Mao
 * Date: 2018-11-08 19:10
 */
public class AppManagerImpl implements AppManager {
    @Autowired
    private AppDao appDao;

    @Override
    public AppVO getDefaultAppVO(){
        return AppConverter.appDO2AppVO(
                appDao.getAppListLimit(1L)
        );
    }
}
