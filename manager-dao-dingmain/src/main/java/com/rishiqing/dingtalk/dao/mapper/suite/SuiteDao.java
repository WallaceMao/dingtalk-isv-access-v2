package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.SuiteDO;
import org.springframework.stereotype.Repository;

@Repository("suiteDao")
public interface SuiteDao {
    /**
     * 保存或者更新suite，如果suite的key值不存在，那么走insert，如果存在，走update
     * @param suiteDO
     */
    void saveOrUpdateSuite(SuiteDO suiteDO);

    /**
     * 获取suite
     * @return
     */
    SuiteDO getSuite();
}
