package com.rishiqing.self.biz.service.impl;

import com.rishiqing.dingtalk.biz.util.CryptoUtil;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-05-23 20:34
 */
public class RsqLoginService {
    @Autowired
    private CryptoUtil cryptoUtil;

    public String generateLoginToken(CorpStaffVO corpStaffVO) {
        String loginStr = makeLoginString(corpStaffVO);
        return cryptoUtil.encrypt(loginStr);
    }

    private String makeLoginString(CorpStaffVO corpStaffVO){
        return String.valueOf(new Date().getTime()) +
                "--" +
                corpStaffVO.getCorpId() +
                "--" +
                corpStaffVO.getUserId();
    }
}
