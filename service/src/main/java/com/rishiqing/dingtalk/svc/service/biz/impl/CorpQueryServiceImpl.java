package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.api.service.biz.CorpQueryService;
import com.rishiqing.dingtalk.mgr.dingmain.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
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
        List<CorpDO> doList = corpManager.listCorpBetweenDate(startDate, endDate);
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
