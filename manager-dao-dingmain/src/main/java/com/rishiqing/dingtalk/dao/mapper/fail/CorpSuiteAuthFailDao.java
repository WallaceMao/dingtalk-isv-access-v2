package com.rishiqing.dingtalk.dao.mapper.fail;

import com.rishiqing.dingtalk.dao.model.fail.CorpSuiteAuthFailDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:44
 */
@Repository("corpSuiteAuthFailDao")
public interface CorpSuiteAuthFailDao {
    /**
     * 创建一个授权失败的对象
     * @param corpSuiteAuthFailDO
     */
    public void saveOrUpdateCorpSuiteAuthFail(CorpSuiteAuthFailDO corpSuiteAuthFailDO);

    /**
     * 根据id删除
     * @param id
     */
    public void deleteCorpSuiteAuthFailById(@Param("id")Long id);

    /**
     * 分页获取CorpSuiteAuth
     * @param offset
     * @param limit
     * @return
     */
    public List<CorpSuiteAuthFailDO> getCorpSuiteAuthFailList(@Param("offset")Integer offset, @Param("limit")Integer limit);
}
