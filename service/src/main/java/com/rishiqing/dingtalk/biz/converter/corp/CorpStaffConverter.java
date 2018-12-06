package com.rishiqing.dingtalk.biz.converter.corp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rishiqing.dingtalk.dao.model.corp.CorpStaffDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffResultVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:59
 */
public class CorpStaffConverter {
    public static CorpStaffDO corpStaffVO2CorpStaffDO(CorpStaffVO staffVO){
        if(null == staffVO){
            return null;
        }
        CorpStaffDO staffDO = new CorpStaffDO();
        staffDO.setGmtCreate(staffVO.getGmtCreate());
        staffDO.setGmtModified(staffVO.getGmtModified());
        staffDO.setCorpId(staffVO.getCorpId());
        staffDO.setUserId(staffVO.getUserId());
        staffDO.setName(staffVO.getName());
        staffDO.setTel(staffVO.getTel());
        staffDO.setWorkPlace(staffVO.getWorkPlace());
        staffDO.setRemark(staffVO.getRemark());
        staffDO.setMobile(staffVO.getMobile());
        staffDO.setEmail(staffVO.getEmail());
        staffDO.setActive(staffVO.getActive());

        staffDO.setOrderInDepts(JSON.toJSONString(staffVO.getOrderInDepts()));
        staffDO.setAdmin(staffVO.getAdmin());
        staffDO.setBoss(staffVO.getBoss());
        staffDO.setDingId(staffVO.getDingId());
        staffDO.setIsLeaderInDepts(JSON.toJSONString(staffVO.getIsLeaderInDepts()));
        staffDO.setHide(staffVO.getHide());
        staffDO.setDepartment(JSON.toJSONString(staffVO.getDepartment()));
        staffDO.setPosition(staffVO.getPosition());
        staffDO.setAvatar(staffVO.getAvatar());
        staffDO.setJobnumber(staffVO.getJobnumber());
        staffDO.setExtattr(JSON.toJSONString(staffVO.getExtattr()));
        staffDO.setSys(staffVO.getSys());
        staffDO.setSysLevel(staffVO.getSysLevel());

        staffDO.setRsqUserId(staffVO.getRsqUserId());
        staffDO.setRsqUsername(staffVO.getRsqUsername());
        staffDO.setRsqLoginToken(staffVO.getRsqLoginToken());
        staffDO.setRsqPassword(staffVO.getRsqPassword());
        staffDO.setUnionId(staffVO.getUnionId());
        staffDO.setScopeVersion(staffVO.getScopeVersion());

        return staffDO;
    }

    public static CorpStaffVO corpStaffDO2CorpStaffVO(CorpStaffDO staffDO){
        if(null == staffDO){
            return null;
        }
        CorpStaffVO staffVO = new CorpStaffVO();
        staffVO.setGmtCreate(staffDO.getGmtCreate());
        staffVO.setGmtModified(staffDO.getGmtModified());
        staffVO.setCorpId(staffDO.getCorpId());
        staffVO.setUserId(staffDO.getUserId());
        staffVO.setName(staffDO.getName());
        staffVO.setTel(staffDO.getTel());
        staffVO.setWorkPlace(staffDO.getWorkPlace());
        staffVO.setRemark(staffDO.getRemark());
        staffVO.setMobile(staffDO.getMobile());
        staffVO.setEmail(staffDO.getEmail());
        staffVO.setActive(staffDO.getActive());
        staffVO.setOrderInDepts(JSON.parseObject(staffDO.getOrderInDepts(), new TypeReference<Map<Long, Long>>(){}));
        staffVO.setAdmin(staffDO.getAdmin());
        staffVO.setBoss(staffDO.getBoss());
        staffVO.setDingId(staffDO.getDingId());
        staffVO.setIsLeaderInDepts(JSON.parseObject(staffDO.getIsLeaderInDepts(), new TypeReference<Map<Long, Boolean>>(){}));
        staffVO.setHide(staffDO.getHide());
        staffVO.setDepartment(JSON.parseObject(staffDO.getDepartment(), new TypeReference<List<Long>>(){}));
        staffVO.setPosition(staffDO.getPosition());
        staffVO.setAvatar(staffDO.getAvatar());
        staffVO.setJobnumber(staffDO.getJobnumber());
        staffVO.setExtattr(JSON.parseObject(staffDO.getExtattr(), new TypeReference<Map<String, String>>(){}));
        staffVO.setSys(staffDO.getSys());
        staffVO.setSysLevel(staffDO.getSysLevel());

        staffVO.setRsqUserId(staffDO.getRsqUserId());
        staffVO.setRsqUsername(staffDO.getRsqUsername());
        staffVO.setRsqPassword(staffDO.getRsqPassword());
        staffVO.setRsqLoginToken(staffDO.getRsqLoginToken());
        staffVO.setUnionId(staffDO.getUnionId());
        staffVO.setScopeVersion(staffDO.getScopeVersion());

        return staffVO;
    }

    public static List<CorpStaffVO> corpStaffDOList2CorpStaffVOList(List<CorpStaffDO> doList) {
        if(doList == null){
            return null;
        }
        List<CorpStaffVO> voList = new ArrayList<>(doList.size());
        for(CorpStaffDO staffDO : doList){
            voList.add(corpStaffDO2CorpStaffVO(staffDO));
        }
        return voList;
    }

    public static CorpStaffResultVO corpStaffVO2CorpStaffResultVO(CorpStaffVO staffVO){
        if(null == staffVO){
            return null;
        }
        CorpStaffResultVO staffResult = new CorpStaffResultVO();
        staffResult.setCorpId(staffVO.getCorpId());
        staffResult.setUserId(staffVO.getUserId());
        staffResult.setName(staffVO.getName());
        staffResult.setOrderInDepts(staffVO.getOrderInDepts());
        staffResult.setAdmin(staffVO.getAdmin());
        staffResult.setBoss(staffVO.getBoss());
        staffResult.setDingId(staffVO.getDingId());
        staffResult.setIsLeaderInDepts(staffVO.getIsLeaderInDepts());
        staffResult.setHide(staffVO.getHide());
        staffResult.setDepartment(staffVO.getDepartment());
        staffResult.setPosition(staffVO.getPosition());
        staffResult.setAvatar(staffVO.getAvatar());
        staffResult.setJobnumber(staffVO.getJobnumber());
        staffResult.setExtattr(staffVO.getExtattr());
        staffResult.setUnionId(staffVO.getUnionId());
        staffResult.setRsqUserId(staffVO.getRsqUserId());
        staffResult.setRsqUsername(staffVO.getRsqUsername());
        staffResult.setRsqLoginToken(staffVO.getRsqLoginToken());
        staffResult.setRsqPassword(staffVO.getRsqPassword());
        return staffResult;
    }
}
