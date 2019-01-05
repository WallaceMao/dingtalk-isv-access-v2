package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpQueryService;
import com.rishiqing.dingtalk.manager.corp.CorpManager;
import com.rishiqing.dingtalk.manager.corp.CorpStaffManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 16:25
 */
public class CorpQueryServiceImpl implements CorpQueryService {
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpStaffManager corpStaffManager;

    @Override
    public List<CorpCountWithCreatorVO> listPageCorpCount(Long pageSize, Long offset, Map<String, Object> clause) {
        List<CorpDO> doList = corpManager.listPageCorpWithCreator(pageSize, offset, clause);
        List<CorpCountWithCreatorVO> voList = new ArrayList<>(doList.size());
        for (CorpDO corpDO : doList) {
            String corpId = corpDO.getCorpId();
            CorpCountWithCreatorVO corpVO = CorpConverter.corpDO2CorpCountWithCreatorVO(corpDO);
            corpVO.setCorpCount(
                    corpStaffManager.countCorpStaffByCorpId(corpId)
            );
            voList.add(corpVO);
        }
        return voList;
    }

    @Override
    public Long getPageCorpTotal(Map<String, Object> clause) {
        return corpManager.countCorpSuiteAuth(clause);
    }

    @Override
    public List<CorpCountWithCreatorVO> listCorpBetweenDate(Date startDate, Date endDate) {
        List<CorpDO> doList = corpManager.listCorpBetweenDate(startDate,endDate);
        List<CorpCountWithCreatorVO> voList = new ArrayList<>(doList.size());
        for (CorpDO corpDO : doList) {
            String corpId = corpDO.getCorpId();
            CorpCountWithCreatorVO corpVO = CorpConverter.corpDO2CorpCountWithCreatorVO(corpDO);
            corpVO.setCorpCount(
                    corpStaffManager.countCorpStaffByCorpId(corpId)
            );
            voList.add(corpVO);
        }
        return voList;
    }


}
