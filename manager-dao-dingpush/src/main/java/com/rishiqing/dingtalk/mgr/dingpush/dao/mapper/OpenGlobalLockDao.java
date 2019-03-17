package com.rishiqing.dingtalk.mgr.dingpush.dao.mapper;

import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenGlobalLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("openGlobalLockDao")
public interface OpenGlobalLockDao {

	public void saveOrUpdateOpenGlobalLock(OpenGlobalLockDO lock);

	public OpenGlobalLockDO getOpenGlobalLockByLockKey(@Param("lockKey") String lockKey);

	public void updateStatus(OpenGlobalLockDO lock);

}

