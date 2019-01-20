package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.SuiteTokenDO;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:52
 */
@Repository("suiteTokenDao")
public interface SuiteTokenDao {
    /**
     * 创建或更新一个套件SuiteToken
     * @param suiteTokenDO
     */
    public void saveOrUpdateSuiteToken(SuiteTokenDO suiteTokenDO);

    /**
     * 根据suiteKey查询套件SuiteToken
     * @return
     */
    public SuiteTokenDO getSuiteToken();
}
