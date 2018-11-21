package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.SuiteTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    void saveOrUpdateSuiteToken(SuiteTokenDO suiteTokenDO);

    /**
     * 根据suiteKey查询套件SuiteToken
     * @return
     */
    SuiteTokenDO getSuiteToken();
}
