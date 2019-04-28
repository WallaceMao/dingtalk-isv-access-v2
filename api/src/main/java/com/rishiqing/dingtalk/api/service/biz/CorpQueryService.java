package com.rishiqing.dingtalk.api.service.biz;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpCountWithCreatorVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 16:26
 */
public interface CorpQueryService {
    List<CorpCountWithCreatorVO> listPageCorpCount(Long pageSize, Long offset, Map<String, Object> clause);

    Long getPageCorpTotal(Map<String, Object> clause);

    List<CorpCountWithCreatorVO> listCorpBetweenDate(Date startDate, Date endDate);

    Boolean isAboveCorpStaffCountThreshold(String corpId);
}
