package com.rishiqing.dingtalk.mgr.dingpush.dao.mapper;

import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("openSyncBizDataDao")
public interface OpenSyncBizDataDao {

	OpenSyncBizDataDO getOpenSyncBizDataById(@Param("id") Long id);

	List<OpenSyncBizDataDO> getOpenSyncBizDataListByStatus(@Param("status") Long status);

	void updateStatus(OpenSyncBizDataDO data);

	List<OpenSyncBizDataDO> getOpenSyncBizDataMediumListByStatus(@Param("status") Long status);

	void updateMediumStatus(OpenSyncBizDataDO openSyncBizDataDO);

    void saveOrUpdateOpenSyncBizDataMedium(OpenSyncBizDataDO data);
}

