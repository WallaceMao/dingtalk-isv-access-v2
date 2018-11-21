package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.SuiteTicketDO;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:50
 */
@Repository("suiteTicketDao")
public interface SuiteTicketDao {
    /**
     * 创建或更新一个套件SuiteTicket
     * @param suiteTicketDO
     */
    void saveOrUpdateSuiteTicket(SuiteTicketDO suiteTicketDO);

    /**
     * 查询套件SuiteTicket
     * @return
     */
    SuiteTicketDO getSuiteTicket();
}
