package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite;

import com.rishiqing.dingtalk.api.model.domain.suite.CorpSuiteAuthDeptDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:41
 */
@Repository("corpSuiteAuthDeptDao")
public interface CorpSuiteAuthDeptDao {
    /**
     * 批量保存corpSuiteAuthDept对象
     *
     * @param corpSuiteAuthDeptDOList
     */
    void saveBatchCorpSuiteAuthDept(List<CorpSuiteAuthDeptDO> corpSuiteAuthDeptDOList);

    /**
     * 根据corpId删除corpSuiteAuthDept
     * @param corpId
     * @return
     */
    void deleteCorpSuiteAuthDeptByCorpId(
            @Param("corpId") String corpId);

    CorpSuiteAuthDeptDO getCorpSuiteAuthDeptByCorpIdAndDeptId(
            @Param("corpId") String corpId,
            @Param("deptId") Long deptId
    );

    List<CorpSuiteAuthDeptDO> listGetCorpSuiteAuthDeptByCorpId(@Param("corpId") String corpId);
}
