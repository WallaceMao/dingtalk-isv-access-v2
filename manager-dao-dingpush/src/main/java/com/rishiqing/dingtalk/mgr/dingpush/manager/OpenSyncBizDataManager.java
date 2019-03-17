package com.rishiqing.dingtalk.mgr.dingpush.manager;

import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:47
 */
public interface OpenSyncBizDataManager {
    List<OpenSyncBizDataVO> getOpenSyncBizDataListByStatus(Long status);

    List<OpenSyncBizDataVO> getOpenSyncBizDataMediumListByStatus(Long status);

    void saveOrUpdateOpenSyncBizDataMedium(OpenSyncBizDataDO data);

    void updateStatus(OpenSyncBizDataVO data);

    void updateMediumStatus(OpenSyncBizDataVO data);
}
