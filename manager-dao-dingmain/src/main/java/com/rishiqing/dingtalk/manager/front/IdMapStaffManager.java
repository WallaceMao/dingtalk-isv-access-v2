package com.rishiqing.dingtalk.manager.front;


import com.rishiqing.dingtalk.isv.api.model.front.IdMapStaffVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:55
 */
public interface IdMapStaffManager {
    /**
     * 根据staff的rsqId获取到userId
     * @param rsqIds
     * @return
     */
    public List<IdMapStaffVO> getUserIdFromRsqId(String corpId, String[] rsqIds);
    /**
     * 根据staff的rsqId获取到userId
     * @param userIds
     * @return
     */
    public List<IdMapStaffVO> getRsqIdFromUserId(String corpId, String[] userIds);
}
