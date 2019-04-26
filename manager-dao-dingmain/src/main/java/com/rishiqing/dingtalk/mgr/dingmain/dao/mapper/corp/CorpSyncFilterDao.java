package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;
import org.springframework.stereotype.Repository;

/**
 * @author: Da Shuai
 * @create: 2019-04-25 17:41
 */
@Repository("corpSyncFilterDao")
public interface CorpSyncFilterDao {
    void saveOrUpdateCorpSyncFilter(CorpSyncFilterDO corpSyncFilterDO);
}
