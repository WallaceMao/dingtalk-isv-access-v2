package com.rishiqing.dingtalk.manager.dingpush.impl;

import com.rishiqing.dingtalk.converter.dingpush.OpenSyncBizDataConverter;
import com.rishiqing.dingtalk.dao.mapper.dingpush.OpenSyncBizDataDao;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.manager.dingpush.OpenSyncBizDataManager;
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
