package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpLockDO;
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
