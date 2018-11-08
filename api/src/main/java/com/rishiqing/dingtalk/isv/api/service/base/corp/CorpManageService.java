package com.rishiqing.dingtalk.isv.api.service.base.corp;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:23
 */
public interface CorpManageService {
    void saveOrUpdateCorp(CorpVO corpVO);

    CorpTokenVO getCorpTokenByCorpId(String corpId);

    CorpVO getCorpByCorpId(String corpId);

    void updateRsqInfo(CorpVO corpVO);
}
