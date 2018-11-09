package com.rishiqing.dingtalk.dao.mapper.front;

import com.rishiqing.dingtalk.dao.model.front.IdMapStaffDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:42
 */
@Repository("idMapStaffDao")
public interface IdMapStaffDao {
    /**
     * 根据staff的rsqId获取到userId
     * @param rsqIds
     * @return
     */
    public List<IdMapStaffDO> getUserIdFromRsqId(
            @Param("corpId") String corpId,
            @Param("rsqIds") String[] rsqIds);
    /**
     * 根据staff的rsqId获取到userId
     * @param userIds
     * @return
     */
    public List<IdMapStaffDO> getRsqIdFromUserId(
            @Param("corpId") String corpId,
            @Param("userIds") String[] userIds);
}
