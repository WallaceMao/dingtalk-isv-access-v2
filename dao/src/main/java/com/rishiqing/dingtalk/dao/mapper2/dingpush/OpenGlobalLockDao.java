package com.rishiqing.dingtalk.dao.mapper2.dingpush;

import com.rishiqing.dingtalk.dao.model.dingpush.OpenGlobalLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("openGlobalLockDao")
public interface OpenGlobalLockDao {

	void saveOrUpdateOpenGlobalLock(OpenGlobalLockDO lock);

	OpenGlobalLockDO getOpenGlobalLockByLockKey(@Param("lockKey") String lockKey);

	void updateStatus(OpenGlobalLockDO lock);

}

