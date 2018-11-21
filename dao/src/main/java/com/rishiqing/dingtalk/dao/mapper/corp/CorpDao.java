package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:09
 */
@Repository("corpDao")
public interface CorpDao {
    /**
     * 创建一个企业信息
     * @param corpDO
     */
    void saveOrUpdateCorp(CorpDO corpDO);

    /**
     * 更新日事清相关信息，例如rsq_id
     * @param corpDO
     * @return
     */
    void updateCorpRsqInfo(CorpDO corpDO);

    /**
     * 根据corpId查询企业
     * @param corpId
     * @return
     */
    CorpDO getCorpByCorpId(@Param("corpId") String corpId);

    /**
     * 根据id查询企业
     * @param id
     * @return
     */
    CorpDO getCorpById(@Param("id") Long id);

    /**
     * 根据id范围查询企业
     * @param fromId
     * @param toId
     * @return
     */
    List<CorpDO> getCorpListByIdRange(@Param("fromId") Long fromId, @Param("toId") Long toId);
}
