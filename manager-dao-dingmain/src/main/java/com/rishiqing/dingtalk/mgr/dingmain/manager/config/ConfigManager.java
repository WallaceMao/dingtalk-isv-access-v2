package com.rishiqing.dingtalk.mgr.dingmain.manager.config;

import com.rishiqing.dingtalk.api.model.domain.config.ConfigDO;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 18:01
 */
public interface ConfigManager {
    ConfigDO getConfigByConfigKey(String configKey);

    void saveOrUpdateConfig(ConfigDO configDO);
}
