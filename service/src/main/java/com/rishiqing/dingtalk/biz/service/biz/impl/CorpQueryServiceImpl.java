package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.biz.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpQueryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 16:25
 */
public class CorpQueryServiceImpl implements CorpQueryService {
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    @Override
    public List<CorpCountWithCreatorVO> listPageCorpCount(Long pageSize, Long offset) {
        List<CorpDO> doList = corpManageService.listPageCorpWithCreator(pageSize, offset);
        List<CorpCountWithCreatorVO> voList = new ArrayList<>(doList.size());
        for (CorpDO corpDO : doList) {
            String corpId = corpDO.getCorpId();
            CorpCountWithCreatorVO corpVO = CorpConverter.corpDO2CorpCountWithCreatorVO(corpDO);
            corpVO.setCorpCount(
                    corpStaffManageService.countCorpStaffByCorpId(corpId)
            );
            voList.add(corpVO);
        }
        return voList;
    }

    @Override
    public Long getPageCorpTotal() {
        return corpManageService.countCorpSuiteAuth();
    }
}
