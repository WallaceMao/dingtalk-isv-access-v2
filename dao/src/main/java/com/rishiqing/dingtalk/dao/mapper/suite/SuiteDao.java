package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.SuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("suiteDao")
public interface SuiteDao {
    /**
     * 根据suiteKey获取suite
     * @param suiteKey
     * @return
     */
    SuiteDO getSuiteBySuiteKey(@Param("suiteKey") String suiteKey);

    /**
     * 保存或者更新suite，如果suite的key值不存在，那么走insert，如果存在，走update
     * @param suiteDO
     */
    void saveOrUpdateSuite(SuiteDO suiteDO);
}
