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
    void saveOrUpdateApp(AppDO appDO);

    /**
     * 根据suiteKey查询为应用
     * @return
     */
    List<AppDO> getAppList();

    /**
     * 根据appId查询为应用
     * @param appId
     * @return
     */
    AppDO getAppByAppId(@Param("appId") Long appId);

    AppDO getAppListLimit(@Param("limit") Long limit);
}
