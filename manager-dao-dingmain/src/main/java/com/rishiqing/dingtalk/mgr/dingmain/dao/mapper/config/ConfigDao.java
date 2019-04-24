package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.config;

import com.rishiqing.dingtalk.api.model.domain.config.ConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:10
 */
@Repository("configDao")
public interface ConfigDao {
    ConfigDO getConfigByConfigKey(@Param("configKey") String configKey);

    void saveOrUpdateConfig(ConfigDO configDO);
}
