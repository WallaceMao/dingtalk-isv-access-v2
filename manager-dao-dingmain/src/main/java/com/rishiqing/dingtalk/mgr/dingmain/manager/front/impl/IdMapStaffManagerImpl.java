package com.rishiqing.dingtalk.mgr.dingmain.manager.front.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.front.IdMapStaffConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.front.IdMapStaffDao;
import com.rishiqing.dingtalk.api.model.vo.front.IdMapStaffVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.front.IdMapStaffManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:56
 */
public class IdMapStaffManagerImpl implements IdMapStaffManager {
    @Autowired
    private IdMapStaffDao idMapStaffDao;

    @Override
    public List<IdMapStaffVO> getUserIdFromRsqId(String corpId, String[] rsqIds) {
        return IdMapStaffConverter.idMapStaffDOList2IdMapStaffVOList(
                idMapStaffDao.getUserIdFromRsqId(corpId, rsqIds)
        );
    }

    @Override
    public List<IdMapStaffVO> getRsqIdFromUserId(String corpId, String[] userIds) {
        return IdMapStaffConverter.idMapStaffDOList2IdMapStaffVOList(
                idMapStaffDao.getRsqIdFromUserId(corpId, userIds)
        );
    }
}
