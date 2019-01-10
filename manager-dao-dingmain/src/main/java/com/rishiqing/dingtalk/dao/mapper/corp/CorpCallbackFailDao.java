package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpCallbackFailDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 16:15
 */
@Repository("corpCallbackFailDao")
public interface CorpCallbackFailDao {
    /**
     * 保存或更新公司回调失败的事件
     * @param corpCallbackFailDO
     */
    public void saveOrUpdateCorpCallbackFail(CorpCallbackFailDO corpCallbackFailDO);

    /**
     * 根据id删除失败回调事件
     * @param id
     */
    public void deleteCorpCallbackFailById(@Param("id") Long id);

    /**
     * 分页获取回调失败事件列表
     * @param offset
     * @param limit
     * @return
     */
    public List<CorpCallbackFailDO> getCorpCallbackFailList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
