package com.rishiqing.dingtalk.web.dingcallbackbizlistener.service;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestJsonHelper;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 21:45
 */
public class CallbackBizDataService {
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpRequestJsonHelper corpRequestJsonHelper;

    public JSONObject fetchDeptBizData(String corpId, Long deptId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        return corpRequestJsonHelper.getCorpDepartment(
                corpTokenVO.getCorpToken(), corpId, deptId);
    }
}
