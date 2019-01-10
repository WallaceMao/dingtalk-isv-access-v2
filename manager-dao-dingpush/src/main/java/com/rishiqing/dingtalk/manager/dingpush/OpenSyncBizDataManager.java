package com.rishiqing.dingtalk.manager.dingpush;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:47
 */
public interface OpenSyncBizDataManager {
    List<OpenSyncBizDataVO> getOpenSyncBizDataListByStatus(Long status);

    List<OpenSyncBizDataVO> getOpenSyncBizDataMediumListByStatus(Long status);

    void updateStatus(OpenSyncBizDataVO data);

    void updateMediumStatus(OpenSyncBizDataVO data);
}
