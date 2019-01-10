package com.rishiqing.dingtalk.dao.mapper.front;

import com.rishiqing.dingtalk.dao.model.front.StaffPopupLogDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:34
 */
@Repository("staffPopupLogDao")
public interface StaffPopupLogDao {
    /**
     * 保存staffPopupLogDO
     * @param staffPopupLogDO
     */
    public void saveOrUpdateStaffPopupLog(StaffPopupLogDO staffPopupLogDO);

    /**
     * 获取staffPopupLog
     * @param corpId
     * @param userId
     * @param popupType
     * @return
     */
    public StaffPopupLogDO getStaffPopupLogByCorpIdAndUserIdAndPopupType(
            @Param("corpId") String corpId,
            @Param("userId") String userId,
            @Param("popupType") String popupType
    );
}
