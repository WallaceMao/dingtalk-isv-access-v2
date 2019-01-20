package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpJSAPITicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:15
 */
@Repository("corpJSAPITicketDao")
public interface CorpJSAPITicketDao {
    /**
     * 创建或更新一个企业的corpJSTicketDO
     * @param corpJSTicketDO
     */
    public void saveOrUpdateCorpJSAPITicket(CorpJSAPITicketDO corpJSTicketDO);

    /**
     * 删除企业JSTicket
     * @param corpId
     */
    public void deleteCorpJSAPITicketByCorpId(@Param("corpId") String corpId);

    /**
     * 获取企业的JSTicket
     * @return
     */
    public CorpJSAPITicketDO getCorpJSAPITicketByCorpId(@Param("corpId") String corpId);
}
