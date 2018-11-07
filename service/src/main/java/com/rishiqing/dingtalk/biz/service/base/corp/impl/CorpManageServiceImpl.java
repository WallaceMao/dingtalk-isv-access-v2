package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpDao;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:56
 */
public class CorpManageServiceImpl implements CorpManageService {
    @Autowired
    private CorpDao corpDao;

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO){
        corpDao.saveOrUpdateCorp(
                CorpConverter.CorpVO2CorpDO(corpVO)
        );
    }
}
