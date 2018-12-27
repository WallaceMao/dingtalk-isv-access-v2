package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 16:26
 */
public interface CorpQueryService {
    List<CorpCountWithCreatorVO> listPageCorpCount(Long pageSize, Long offset, Map<String, Object> clause);

    Long getPageCorpTotal();
}
