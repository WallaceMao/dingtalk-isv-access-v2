package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:31
 */
@Repository("corpTokenDao")
public interface CorpTokenDao {
    /**
     * 创建或更新一个企业的corpTokenDO
     * @param corpTokenDO
     */
    void saveOrUpdateCorpToken(CorpTokenDO corpTokenDO);

    /**
     * 删除企业token
     * @param corpId
     */
    void deleteCorpTokenByCorpId(@Param("corpId") String corpId);

    /**
     * 根据corpId获取CorpToken
     * @param corpId
     * @return
     */
    CorpTokenDO getCorpTokenByCorpId(@Param("corpId") String corpId);
}
