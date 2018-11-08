package com.rishiqing.dingtalk.isv.api.service.base.suite;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:57
 */
public interface CorpAppManageService {
    void saveOrUpdateCorpApp(CorpAppVO corpAppVO);

    CorpAppVO getCorpAppByCorpIdAndAppId(String corpId, Long appId);
}
