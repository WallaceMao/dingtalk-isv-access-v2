package com.rishiqing.dingtalk.mgr.dingpush.manager.impl;

import com.rishiqing.dingtalk.mgr.dingpush.converter.OpenSyncBizDataConverter;
import com.rishiqing.dingtalk.mgr.dingpush.dao.mapper.OpenSyncBizDataDao;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:48
 */
public class OpenSyncBizDataManagerImpl implements OpenSyncBizDataManager {
    @Autowired
    private OpenSyncBizDataDao openSyncBizDataDao;

    @Override
    public List<OpenSyncBizDataVO> getOpenSyncBizDataListByStatus(Long status) {
        return OpenSyncBizDataConverter.openSyncBizDataDOList2openSyncBizDataVOList(
                openSyncBizDataDao.getOpenSyncBizDataListByStatus(status)
        );
    }

    @Override
    public List<OpenSyncBizDataVO> getOpenSyncBizDataMediumListByStatus(Long status) {
        return OpenSyncBizDataConverter.openSyncBizDataDOList2openSyncBizDataVOList(
                openSyncBizDataDao.getOpenSyncBizDataMediumListByStatus(status)
        );
    }

    @Override
    public void saveOrUpdateOpenSyncBizDataMedium(OpenSyncBizDataDO data) {
        openSyncBizDataDao.saveOrUpdateOpenSyncBizDataMedium(data);
    }

    @Override
    public void updateStatus(OpenSyncBizDataVO data) {
        openSyncBizDataDao.updateStatus(
                OpenSyncBizDataConverter.openSyncBizDataVO2openSyncBizDataDO(data)
        );
    }

    @Override
    public void updateMediumStatus(OpenSyncBizDataVO data) {
        openSyncBizDataDao.updateMediumStatus(
                OpenSyncBizDataConverter.openSyncBizDataVO2openSyncBizDataDO(data)
        );
    }
}
