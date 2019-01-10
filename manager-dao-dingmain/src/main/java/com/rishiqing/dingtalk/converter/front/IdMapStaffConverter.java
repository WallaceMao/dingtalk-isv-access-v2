package com.rishiqing.dingtalk.converter.front;

import com.rishiqing.dingtalk.dao.model.front.IdMapStaffDO;
import com.rishiqing.dingtalk.isv.api.model.front.IdMapStaffVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:57
 */
public class IdMapStaffConverter {
    public static IdMapStaffVO idMapStaffDO2IdMapStaffVO(IdMapStaffDO idMapStaffDO){
        if(idMapStaffDO == null){
            return null;
        }
        IdMapStaffVO vo = new IdMapStaffVO();
        vo.setUserId(idMapStaffDO.getUserId());
        vo.setRsqUserId(idMapStaffDO.getRsqUserId());
        vo.setName(idMapStaffDO.getName());
        vo.setEmplId(idMapStaffDO.getEmplId());
        vo.setAvatar(idMapStaffDO.getAvatar());
        return vo;
    }

    public static List<IdMapStaffVO> idMapStaffDOList2IdMapStaffVOList(List<IdMapStaffDO> doList){
        if(doList == null){
            return null;
        }
        List<IdMapStaffVO> voList = new ArrayList<>(doList.size());
        for(IdMapStaffDO staffDO : doList){
            voList.add(idMapStaffDO2IdMapStaffVO(staffDO));
        }
        return voList;
    }
}
