package com.rishiqing.dingtalk.dao.mapper.fail;

import com.rishiqing.dingtalk.dao.model.fail.CorpOrgSyncFailDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:19
 */
@Repository("corpOrgSyncFailDao")
public interface CorpOrgSyncFailDao {
    /**
     * 创建一个企业组织机构信息获取失败的对象
     * @param corpOrgSyncFailDO
     */
    void saveOrUpdateCorpOrgSyncFail(CorpOrgSyncFailDO corpOrgSyncFailDO);

    /**
     * 根据id删除
     * @param id
     */
    void deleteCorpOrgSyncFailById(@Param("id") Long id);

    /**
     * 分页获取失败时间的记录列表
     * @param offset
     * @param limit
     * @return
     */
    List<CorpOrgSyncFailDO> getCorpOrgSyncFailList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
