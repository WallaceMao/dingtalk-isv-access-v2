package com.rishiqing.dingtalk.dao.mapper.suite;

import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:41
 */
@Repository("corpSuiteAuthDao")
public interface CorpSuiteAuthDao {
    /**
     * 创建一个企业对套件授权对象
     *
     * @param corpSuiteAuthDO
     */
    public void saveOrUpdateCorpSuiteAuth(CorpSuiteAuthDO corpSuiteAuthDO);

    /**
     * 根据corpId解除企业对套件的授权
     * @param corpId
     * @return
     */
    public void deleteCorpSuiteAuthByCorpId(@Param("corpId") String corpId);

    /**
     * 根据corpId查询授权信息
     * @param corpId
     * @return
     */
    public CorpSuiteAuthDO getCorpSuiteAuthByCorpId(@Param("corpId") String corpId);

//    /**
//     * 分页查询被授权的企业
//     *
//     * @param startRow
//     * @param pageSize
//     * @return
//     */
//    List<CorpSuiteAuthDO> getCorpSuiteAuthByPage(@Param("startRow") int startRow,
//                                                 @Param("pageSize") int pageSize);
}
