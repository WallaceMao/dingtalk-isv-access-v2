package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestCommonHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestHelper;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 13:45
 */
public class CorpTopRequestHelper implements CorpRequestHelper {
    private CorpRequestCommonHelper corpRequestCommonHelper;

    @Autowired
    public CorpTopRequestHelper(CorpRequestCommonHelper corpRequestCommonHelper) {
        this.corpRequestCommonHelper = corpRequestCommonHelper;
    }

    /**
     * 获取部门详情
     *
     * @param token
     * @param corpId
     * @param deptId
     * @return
     */
    @Override
    public CorpDepartmentVO getCorpDepartment(String token, String corpId, Long deptId) {
        OapiDepartmentGetResponse resp = corpRequestCommonHelper.getCorpDepartment(token, deptId);
        CorpDepartmentVO dept = new CorpDepartmentVO();
        dept.setCorpId(corpId);
        dept.setDeptId(resp.getId());
        dept.setName(resp.getName());
        dept.setOrder(resp.getOrder());
        dept.setParentId(resp.getParentid());
        dept.setCreateDeptGroup(resp.getCreateDeptGroup());
        dept.setAutoAddUser(resp.getAutoAddUser());
        dept.setDeptHiding(resp.getDeptHiding());
        dept.setDeptPerimits(resp.getDeptPerimits());
        dept.setUserPerimits(resp.getUserPerimits());
        dept.setOuterDept(resp.getOuterDept());
        dept.setOuterPermitDepts(resp.getOuterPermitDepts());
        dept.setOuterPermitUsers(resp.getOuterPermitUsers());
        dept.setOrgDeptOwner(resp.getOrgDeptOwner());
        dept.setDeptManagerUseridList(resp.getDeptManagerUseridList());
        return dept;
    }

    /**
     * 获取子部门列表
     *
     *
     * @param token
     * @param corpId
     * @param parentDeptId
     * @return
     */
    @Override
    public List<CorpDepartmentVO> getChildCorpDepartment(String token, String corpId, Long parentDeptId) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest req = new OapiDepartmentListRequest();
        req.setId(String.valueOf(parentDeptId));
        req.setFetchChild(false);
        req.setHttpMethod("GET");
        try {
            OapiDepartmentListResponse resp = client.execute(req, token);
            List<OapiDepartmentListResponse.Department> list = resp.getDepartment();
            List<CorpDepartmentVO> deptList = new ArrayList<>(list.size());
            for (OapiDepartmentListResponse.Department dingDept : list) {
                CorpDepartmentVO dept = new CorpDepartmentVO();
                dept.setCorpId(corpId);
                dept.setDeptId(dingDept.getId());
                dept.setName(dingDept.getName());
                dept.setParentId(dingDept.getParentid());
                dept.setCreateDeptGroup(dingDept.getCreateDeptGroup());
                dept.setAutoAddUser(dingDept.getAutoAddUser());
                deptList.add(dept);
            }
            return deptList;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public CorpStaffVO getCorpStaff(String token, String corpId, String userId) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest req = new OapiUserGetRequest();
        req.setUserid(userId);
        req.setHttpMethod("GET");
        try {
            OapiUserGetResponse resp = client.execute(req, token);
            CorpStaffVO corpStaff = new CorpStaffVO();
            corpStaff.setCorpId(corpId);
            corpStaff.setUserId(resp.getUserid());
            corpStaff.setName(resp.getName());
            corpStaff.setTel(resp.getTel());
            corpStaff.setWorkPlace(resp.getWorkPlace());
            corpStaff.setRemark(resp.getRemark());
            corpStaff.setMobile(resp.getMobile());
            corpStaff.setEmail(resp.getEmail());
            corpStaff.setActive(resp.getActive());
            corpStaff.setOrderInDepts(JSON.parseObject(resp.getOrderInDepts(), new TypeReference<Map<Long, Long>>() {
            }));
            corpStaff.setBoss(resp.getIsBoss());
            corpStaff.setAdmin(resp.getIsAdmin());
            corpStaff.setDingId(resp.getDingId());
            corpStaff.setIsLeaderInDepts(JSON.parseObject(resp.getIsLeaderInDepts(), new TypeReference<Map<Long, Boolean>>() {
            }));
            corpStaff.setHide(resp.getIsHide());
            corpStaff.setDepartment(resp.getDepartment());
            corpStaff.setPosition(resp.getPosition());
            corpStaff.setAvatar(resp.getAvatar());
            corpStaff.setJobnumber(resp.getJobnumber());
            corpStaff.setExtattr(JSON.parseObject(resp.getExtattr(), new TypeReference<Map<String, String>>() {
            }));
            corpStaff.setUnionId(resp.getUnionid());

            return corpStaff;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> getCorpDepartmentStaffByPage(String token, String corpId, Long deptId, Long offset, Long size) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/list");
        OapiUserListRequest req = new OapiUserListRequest();
        req.setDepartmentId(deptId);
        req.setOffset(offset);
        req.setSize(size);
        req.setHttpMethod("GET");
        try {
            OapiUserListResponse resp = client.execute(req, token);
            List<OapiUserListResponse.Userlist> list = resp.getUserlist();
            List<CorpStaffVO> staffList = new ArrayList<>(list.size());
            for (OapiUserListResponse.Userlist user : list) {
                CorpStaffVO corpStaff = new CorpStaffVO();
                corpStaff.setCorpId(corpId);
                Map<Long, Long> orderMap = new HashMap<>();
                Map<Long, Boolean> isLeaderMap = new HashMap<>();
                orderMap.put(deptId, user.getOrder());
                isLeaderMap.put(deptId, user.getIsLeader());

                corpStaff.setUserId(user.getUserid());
                corpStaff.setName(user.getName());
                corpStaff.setTel(user.getTel());
                corpStaff.setWorkPlace(user.getWorkPlace());
                corpStaff.setRemark(user.getRemark());
                corpStaff.setMobile(user.getMobile());
                corpStaff.setEmail(user.getEmail());
                corpStaff.setActive(user.getActive());
                corpStaff.setOrderInDepts(orderMap);
                corpStaff.setBoss(user.getIsBoss());
                corpStaff.setAdmin(user.getIsAdmin());
                corpStaff.setDingId(user.getDingId());
                corpStaff.setIsLeaderInDepts(isLeaderMap);
                corpStaff.setHide(user.getIsHide());
                corpStaff.setDepartment((List<Long>) JSON.parse(user.getDepartment()));
                corpStaff.setPosition(user.getPosition());
                corpStaff.setAvatar(user.getAvatar());
                corpStaff.setJobnumber(user.getJobnumber());
                corpStaff.setExtattr((Map<String, String>) JSON.parse(user.getExtattr()));
                corpStaff.setUnionId(user.getUnionid());

                staffList.add(corpStaff);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("hasMore", resp.getHasMore());
            result.put("list", staffList);
            return result;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public CorpStaffVO getCorpStaffByAuthCode(String token, String corpId, String authCode) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        try {
            OapiUserGetuserinfoResponse resp = client.execute(request, token);
            CorpStaffVO staffVO = new CorpStaffVO();
            staffVO.setCorpId(corpId);
            staffVO.setUserId(resp.getUserid());
            return staffVO;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }
}
