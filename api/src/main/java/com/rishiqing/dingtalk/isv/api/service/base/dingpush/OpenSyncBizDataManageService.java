package com.rishiqing.dingtalk.isv.api.service.base.dingpush;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:47
 */
public interface OpenSyncBizDataManageService {
    List<OpenSyncBizDataVO> getOpenSyncBizDataListByStatus(Long status);

    void updateStatus(OpenSyncBizDataVO data);
}
