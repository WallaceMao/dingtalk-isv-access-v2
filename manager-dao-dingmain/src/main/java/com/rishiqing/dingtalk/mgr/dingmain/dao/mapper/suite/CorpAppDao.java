package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.CorpAppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:40
 */
@Repository("corpAppDao")
public interface CorpAppDao {
    /**
     * 创建一个企业使用微应用记录
     * @param corpAppDO
     */
    public void saveOrUpdateCorpApp(CorpAppDO corpAppDO);

    /**
     * 删除一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public void deleteCorpAppByCorpIdAndAppId(@Param("corpId")String corpId,@Param("appId")Long appId);

    /**
     * 获取一个企业使用微应用记录
     * @param corpId
     * @param appId
     */
    public CorpAppDO getCorpAppByCorpIdAndAppId(@Param("corpId")String corpId, @Param("appId")Long appId);
}
