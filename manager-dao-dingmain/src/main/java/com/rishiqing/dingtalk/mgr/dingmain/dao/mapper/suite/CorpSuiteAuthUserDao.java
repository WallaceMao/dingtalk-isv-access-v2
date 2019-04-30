package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.CorpSuiteAuthUserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:41
 */
@Repository("corpSuiteAuthUserDao")
public interface CorpSuiteAuthUserDao {
    /**
     * 批量保存corpSuiteAuthUser对象
     *
     * @param corpSuiteAuthUserDOList
     */
    void saveBatchCorpSuiteAuthUser(List<CorpSuiteAuthUserDO> corpSuiteAuthUserDOList);

    /**
     * 根据corpId删除corpSuiteAuthUser
     * @param corpId
     * @return
     */
    void deleteCorpSuiteAuthUserByCorpId(@Param("corpId") String corpId);

    CorpSuiteAuthUserDO getCorpSuiteAuthUserByCorpIdAndUserId(
            @Param("corpId") String corpId,
            @Param("userId") String userId
    );

    List<CorpSuiteAuthUserDO> listGetCorpSuiteAuthUserByCorpId(@Param("corpId") String corpId);
}
