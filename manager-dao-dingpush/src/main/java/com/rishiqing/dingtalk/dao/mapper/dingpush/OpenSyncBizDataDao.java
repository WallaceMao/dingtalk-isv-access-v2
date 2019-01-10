package com.rishiqing.dingtalk.dao.mapper.dingpush;

import com.rishiqing.dingtalk.dao.model.dingpush.OpenSyncBizDataDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("openSyncBizDataDao")
public interface OpenSyncBizDataDao {

	public OpenSyncBizDataDO getOpenSyncBizDataById(@Param("id") Long id);

	public List<OpenSyncBizDataDO> getOpenSyncBizDataListByStatus(@Param("status") Long status);

	public void updateStatus(OpenSyncBizDataDO data);

	List<OpenSyncBizDataDO> getOpenSyncBizDataMediumListByStatus(Long status);

	void updateMediumStatus(OpenSyncBizDataDO openSyncBizDataDO);
}

