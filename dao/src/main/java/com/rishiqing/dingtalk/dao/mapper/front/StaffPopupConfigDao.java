package com.rishiqing.dingtalk.dao.mapper.front;

import com.rishiqing.dingtalk.dao.model.front.StaffPopupConfigDO;
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
    void saveOrUpdateStaffPopupConfig(StaffPopupConfigDO staffPopupConfigDO);

    /**
     * 获取指定suiteKey下的所有popupConfig
     * @return
     */
    List<StaffPopupConfigDO> getStaffPopupConfigListByIsDisabled(@Param("isDisabled") Boolean isDisabled);
}
