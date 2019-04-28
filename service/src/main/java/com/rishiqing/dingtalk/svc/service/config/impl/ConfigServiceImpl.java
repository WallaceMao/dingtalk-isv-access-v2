package com.rishiqing.dingtalk.svc.service.config.impl;

import com.rishiqing.dingtalk.api.model.domain.config.ConfigDO;
import com.rishiqing.dingtalk.api.service.config.ConfigService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.config.ConfigManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:49
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
    private static final String STAFF_COUNT_LIMIT_CONFIG_KEY = "staff_count_limit";
    @Autowired
    private ConfigManager configManager;
    @Autowired
    private CorpManager corpManager;
    /**
     * 员工总数是否高于阈值
     * 1.开启事务
     * 2.获取Config对象的信息
     * 3.如果记录不存在，那么插入初始化记录：关闭阈值开关，并设置阈值为100
     * 4.如果记录存在，那么检查阈值开关
     * 5.如果开：检查是否高于阈值，高于阈值返回true
     * 6.如果关，直接返回true
     *
     * @param staffCount
     * @return
     */
    @Override
    @Transactional
    public Boolean isStaffCountAboveThreshold(Long staffCount) {
        ConfigDO configByConfigKey = configManager.getConfigByConfigKey(STAFF_COUNT_LIMIT_CONFIG_KEY);
        if (configByConfigKey == null) {
            //初始化记录
            ConfigDO initConfigDO = new ConfigDO();
            initConfigDO.setConfigKey(STAFF_COUNT_LIMIT_CONFIG_KEY);
            initConfigDO.setAutoFilter(false);
            initConfigDO.setAutoFilterThreshold(100L);
            configManager.saveOrUpdateConfig(initConfigDO);
            configByConfigKey = initConfigDO;
        }
        Boolean autoFilter = configByConfigKey.getAutoFilter();
        //开启状态
        if (autoFilter) {
            Long autoFilterThreshold = configByConfigKey.getAutoFilterThreshold();
            //高于阈值返回true，低于或等于返回false
            return staffCount > autoFilterThreshold;
        } else {
            return false;
        }
    }

    /**
     * 关闭人数限制过滤器
     */
    @Override
    public void closeStaffCountLimitFilter() {
        ConfigDO configByConfigKey = configManager.getConfigByConfigKey(STAFF_COUNT_LIMIT_CONFIG_KEY);
        if (configByConfigKey == null) {
            logger.warn("configByConfigKey==null");
            return;
        }
        //如果为开启状态，则关闭
        if (configByConfigKey.getAutoFilter()) {
            configByConfigKey.setAutoFilter(false);
            configManager.saveOrUpdateConfig(configByConfigKey);
        }
    }
}
