package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpChargeStatusDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:07
 */
@Repository("corpChargeStatusDao")
public interface CorpChargeStatusDao {

    /**
     * 保存corpChargeStatusDO
     * @param corpChargeStatusDO
     */
    public void saveOrUpdateCorpChargeStatus(CorpChargeStatusDO corpChargeStatusDO);

    /**
     * 根据suiteKey和corpId获取唯一的CorpChargeStatus
     * @param corpId
     * @return
     */
    public CorpChargeStatusDO getCorpChargeStatusByCorpId(@Param("corpId") String corpId);

}
