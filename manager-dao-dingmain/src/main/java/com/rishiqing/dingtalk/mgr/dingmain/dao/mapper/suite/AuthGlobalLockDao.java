package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.AuthGlobalLockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("authGlobalLockDao")
public interface AuthGlobalLockDao {

	void saveOrUpdateAuthGlobalLock(AuthGlobalLockDO lock);

	AuthGlobalLockDO getAuthGlobalLockByLockKey(@Param("lockKey") String lockKey);

	void updateStatus(AuthGlobalLockDO lock);

}

