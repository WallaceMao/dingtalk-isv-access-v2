package com.rishiqing.dingtalk.svc.converter.corp;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffResultVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:59
 */
public class CorpStaffConverter {
    public static CorpStaffResultVO corpStaffVO2CorpStaffResultVO(CorpStaffVO staffVO) {
        if (null == staffVO) {
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
