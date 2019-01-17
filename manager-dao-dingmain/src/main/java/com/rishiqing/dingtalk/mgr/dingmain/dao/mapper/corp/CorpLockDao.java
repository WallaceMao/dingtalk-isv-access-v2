package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:18
 */
@Repository("corpLockDao")
public interface CorpLockDao {
    /**
     * 创建或更新一个lock
     * @param corpLockDO
     */
    public void saveOrUpdateCorpLock(CorpLockDO corpLockDO);

    /**
     * 根据lock_key查询lock
     * @param lockKey
     * @return
     */
    public CorpLockDO getCorpLockByLockKey(@Param("lockKey") String lockKey);
}
