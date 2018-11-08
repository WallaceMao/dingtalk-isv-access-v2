package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.AppDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:39
 */
@Repository("appDao")
public interface AppDao {
    /**
     * 创建一个套件
     * @param appDO
     */
    public void saveOrUpdateApp(AppDO appDO);

    /**
     * 根据suiteKey查询为应用
     * @return
     */
    public List<AppDO> getAppList();

    /**
     * 根据appId查询为应用
     * @param appId
     * @return
     */
    public AppDO getAppByAppId(@Param("appId") Long appId);

    public AppDO getAppListLimit(@Param("limit") Long limit);
}
