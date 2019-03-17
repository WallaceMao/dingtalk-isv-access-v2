package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.SuiteTicketDO;
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
    public void saveOrUpdateSuiteTicket(SuiteTicketDO suiteTicketDO);

    /**
     * 查询套件SuiteTicket
     * @return
     */
    public SuiteTicketDO getSuiteTicket();
}
