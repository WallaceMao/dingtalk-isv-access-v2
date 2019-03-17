package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.front;

import com.rishiqing.dingtalk.api.model.domain.front.StaffPopupConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:33
 */
@Repository("staffPopupConfigDao")
public interface StaffPopupConfigDao {
    /**
     * 保存staffPopupConfigDO
     * @param staffPopupConfigDO
     */
    public void saveOrUpdateStaffPopupConfig(StaffPopupConfigDO staffPopupConfigDO);

    /**
     * 获取指定suiteKey下的所有popupConfig
     * @return
     */
    public List<StaffPopupConfigDO> getStaffPopupConfigListByIsDisabled(@Param("isDisabled") Boolean isDisabled);
}
