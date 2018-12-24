package com.rishiqing.dingtalk.manager.suite.impl;

import com.rishiqing.dingtalk.converter.suite.CorpAppConverter;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpAppDao;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.manager.suite.CorpAppManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:57
 */
public class CorpAppManagerImpl implements CorpAppManager {
    @Autowired
    private CorpAppDao corpAppDao;

    @Override
    public void saveOrUpdateCorpApp(CorpAppVO corpAppVO){
        corpAppDao.saveOrUpdateCorpApp(
                CorpAppConverter.corpAppVO2CorpAppDO(corpAppVO)
        );
    }

    @Override
    public CorpAppVO getCorpAppByCorpIdAndAppId(String corpId, Long appId) {
        return CorpAppConverter.corpAppDO2CorpAppVO(
                corpAppDao.getCorpAppByCorpIdAndAppId(corpId, appId)
        );
    }
}
