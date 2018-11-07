package com.rishiqing.dingtalk.dao.mapper.dingpush;

import com.rishiqing.dingtalk.dao.model.dingpush.OpenGlobalLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("openGlobalLockDao")
public interface OpenGlobalLockDao {

	public void saveOrUpdateOpenGlobalLock(OpenGlobalLockDO lock);

	public OpenGlobalLockDO getOpenGlobalLockByLockKey(@Param("lockKey") String lockKey);

	public void updateStatus(OpenGlobalLockDO lock);

}

