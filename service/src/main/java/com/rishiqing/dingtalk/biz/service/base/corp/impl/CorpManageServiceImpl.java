package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.rishiqing.dingtalk.biz.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.biz.converter.corp.CorpTokenConverter;
import com.rishiqing.dingtalk.biz.enmutype.CorpLockType;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.biz.service.util.CorpLockService;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpDao;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpTokenDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpLockDO;
import com.rishiqing.dingtalk.dao.model.corp.CorpTokenDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:56
 */
public class CorpManageServiceImpl implements CorpManageService {
    @Autowired
    private CorpDao corpDao;
    @Autowired
    private CorpTokenDao corpTokenDao;
    @Autowired
    private CorpLockService corpLockService;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteRequestHelper suiteTopRequestHelper;

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO){
        corpDao.saveOrUpdateCorp(
                CorpConverter.CorpVO2CorpDO(corpVO)
        );
    }

    @Override
    public CorpTokenVO getCorpTokenByCorpId(String corpId){
        CorpTokenDO corpTokenDO = corpTokenDao.getCorpTokenByCorpId(corpId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新corptoken
        //如果过期重新请求
        if (null == corpTokenDO || calendar.getTime().compareTo(corpTokenDO.getExpiredTime()) < 0) {
            CorpLockDO lock = corpLockService.requireLock(corpId, CorpLockType.TOKEN);
            //  如果获取到锁，则更新token
            if(null != lock){
                CorpTokenVO corpTokenVO = suiteTopRequestHelper.getCorpToken(corpId);
                corpTokenDO = CorpTokenConverter.CorpTokenVO2CorpTokenDO(corpTokenVO);
                corpTokenDao.saveOrUpdateCorpToken(corpTokenDO);

                //  释放锁
                corpLockService.releaseLock(corpId, CorpLockType.TOKEN);
            }

        }
        return CorpTokenConverter.CorpTokenDO2CorpTokenVO(corpTokenDO);
    }

    @Override
    public CorpVO getCorpByCorpId(String corpId) {
        return CorpConverter.CorpDO2CorpVO(
                corpDao.getCorpByCorpId(corpId)
        );
    }

    @Override
    public void updateRsqInfo(CorpVO corpVO) {
        corpDao.updateCorpRsqInfo(
                CorpConverter.CorpVO2CorpDO(corpVO)
        );
    }
}
