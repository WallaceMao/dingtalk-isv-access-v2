package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.util;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-01-26 17:20
 */
public class TestCorpFactory {
    public static CorpVO getCorpVO() {
        CorpVO corpVO = new CorpVO();
        Date now = new Date();
        Long mills = now.getTime();

        corpVO.setCorpId("ding" + mills);
        corpVO.setCorpName("auto_test_corp_name_" + mills);
        corpVO.setScopeVersion(mills);

        return corpVO;
    }
}
