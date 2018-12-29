package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:09
 */
@Repository("corpDao")
public interface CorpDao {
    /**
     * 创建一个企业信息
     *
     * @param corpDO
     */
    void saveOrUpdateCorp(CorpDO corpDO);

    /**
     * 更新日事清相关信息，例如rsq_id
     *
     * @param corpDO
     * @return
     */
    void updateCorpRsqInfo(CorpDO corpDO);

    /**
     * 根据corpId查询企业
     *
     * @param corpId
     * @return
     */
    CorpDO getCorpByCorpId(@Param("corpId") String corpId);

    /**
     * 根据id查询企业
     *
     * @param id
     * @return
     */
    CorpDO getCorpById(@Param("id") Long id);

    /**
     * 根据id范围查询企业
     *
     * @param fromId
     * @param toId
     * @return
     */
    List<CorpDO> getCorpListByIdRange(
            @Param("fromId") Long fromId,
            @Param("toId") Long toId);


    /**
     * 分页获取公司（包括开通公司的管理员）
     * @param size
     * @param offset
     * @return
     */
    List<CorpDO> listPageCorpWithCreator(
            @Param("size") Long size,
            @Param("offset") Long offset,
            @Param("clause") Map<String, Object> clause
    );

    Long countCorp();

    Long countCorpSuiteAuth();

    /**
     * 根据创建时间范围查询企业
     * @param startDate
     * @param endDate
     * @return
     */
    List<CorpDO> getCorpListBetweenDate(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
