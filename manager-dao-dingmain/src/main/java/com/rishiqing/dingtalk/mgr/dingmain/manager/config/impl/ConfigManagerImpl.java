package com.rishiqing.dingtalk.mgr.dingmain.manager.config.impl;

import com.rishiqing.dingtalk.api.model.domain.config.ConfigDO;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.config.ConfigDao;
import com.rishiqing.dingtalk.mgr.dingmain.manager.config.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 18:02
 */
@Component
public class ConfigManagerImpl implements ConfigManager {
    @Autowired
    private ConfigDao configDao;
    @Override
    public ConfigDO getConfigByConfigKey(String configKey) {
        return configDao.getConfigByConfigKey(configKey);
    }

    @Override
    public void saveOrUpdateConfig(ConfigDO configDO) {
        configDao.saveOrUpdateConfig(configDO);
    }
}
