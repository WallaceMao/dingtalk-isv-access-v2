package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpMessagePublishLogDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 11:12
 */
@Repository("corpMessagePublishLogDao")
public interface CorpMessagePublishLogDao {
    void saveOrUpdateCorpMessagePublishLog(CorpMessagePublishLogDO corpMessagePublishLogDO);

    CorpMessagePublishLogDO getCorpMessagePublishLogByCorpIdOrderByIdDescWithLimit (
            @Param("corpId") String corpId,
            @Param("limit") Long limit);
}
