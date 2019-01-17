package com.rishiqing.dingtalk.req.rsq.auth.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.http.HttpRequestClient;
import com.rishiqing.common.http.exception.HttpRequestException;
import com.rishiqing.common.http.util.HttpPathUtil;
import com.rishiqing.dingtalk.req.rsq.auth.converter.RsqCorpConverter;
import com.rishiqing.dingtalk.req.rsq.auth.converter.RsqDepartmentConverter;
import com.rishiqing.dingtalk.req.rsq.auth.converter.RsqUserConverter;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.api.model.rsq.RsqCorp;
import com.rishiqing.dingtalk.api.model.rsq.RsqDepartment;
import com.rishiqing.dingtalk.api.model.rsq.RsqUser;
import com.rishiqing.dingtalk.req.rsq.auth.http.RsqRequestHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 与日事清服务器发送公司和账号创建请求的helper
 * Created by Wallace on 2016/11/19.
 */
public class RsqApiRequestHelper implements RsqRequestHelper {
    private static final String URL_CREATE_TEAM = "/api/v3w/tokenAuth/team";
    private static final String URL_CREATE_DEPARTMENT = "/api/v3w/tokenAuth/department";
    private static final String URL_UPDATE_DEPARTMENT = "/api/v3w/tokenAuth/department/{outerId}";
    private static final String URL_DELETE_DEPARTMENT = "/api/v3w/tokenAuth/department/{outerId}";
    private static final String URL_CREATE_USER = "/api/v3w/tokenAuth/user";
    private static final String URL_UPDATE_USER = "/api/v3w/tokenAuth/user/{outerId}";
    private static final String URL_UPDATE_USER_SET_ADMIN = "/api/v3w/tokenAuth/user/{outerId}/admin/{isAdmin}";
    private static final String URL_UPDATE_USER_REMOVE_TEAM = "/api/v3w/tokenAuth/user/{outerId}/team/null";
    private static final Long rootDeptId = 1L;

    private HttpRequestClient httpRequestClient;
    private String rsqDomain;
    private String rsqUrlInternal;

    public HttpRequestClient getHttpRequestClient() {
        return httpRequestClient;
    }

    public void setHttpRequestClient(HttpRequestClient httpRequestClient) {
        this.httpRequestClient = httpRequestClient;
    }

    public String getRsqDomain() {
        return rsqDomain;
    }

    public void setRsqDomain(String rsqDomain) {
        this.rsqDomain = rsqDomain;
    }

    public void setRsqUrlInternal(String rsqUrlInternal) {
        this.rsqUrlInternal = rsqUrlInternal;
    }

    /**
     * 创建公司
     *
     * @param suiteVO
     * @param corpVO
     * @param creator
     * @return
     */
    @Override
    public RsqCorp createCorp(SuiteVO suiteVO, CorpVO corpVO, CorpStaffVO creator) {
//        String url = this.rsqUrlInternal + "/task/v2/tokenAuth/autoCreate/createTeam?token=" + suiteVO.getRsqAppToken();
        String url = this.rsqUrlInternal + URL_CREATE_TEAM;
        JSONObject params = new JSONObject();
        params.put("client", suiteVO.getRsqAppName());
        params.put("name", corpVO.getCorpName());
        params.put("outerCorpId", corpVO.getCorpId());
        if (creator != null) {
            JSONObject creatorMap = new JSONObject();
            creatorMap.put("outerCombineId", generateOuterCombineId(creator));
            creatorMap.put("outerCorpId", creator.getCorpId());
            creatorMap.put("name", creator.getName());
            creatorMap.put("avatar", creator.getAvatar());
            creatorMap.put("outerUnionId", creator.getUnionId());
            params.put("creator", creatorMap);
        }
        String sr = httpRequestClient.httpPostJson(url,
                JSON.toJSONString(params),
                generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createCorp error: " + sr);
        }
        return RsqCorpConverter.JSON2RsqCorp(jsonObject);
    }

    /**
     * 创建部门
     *
     * @param suiteVO
     * @param corpVO
     * @return
     */
    @Override
    public RsqDepartment createDepartment(SuiteVO suiteVO, CorpVO corpVO, CorpDepartmentVO departmentVO, CorpDepartmentVO parentCorpDepartmentVO) {
        String url = this.rsqUrlInternal + URL_CREATE_DEPARTMENT;
        JSONObject params = new JSONObject();
        params.put("name", departmentVO.getName());
        params.put("outerCorpId", corpVO.getCorpId());
        params.put("outerCombineId", generateOuterCombineId(departmentVO));
        params.put("displayOrder", departmentVO.getOrder());
        params.put("outerParentCombineId", generateOuterCombineId(departmentVO.getCorpId(), departmentVO.getParentId()));
        if (departmentVO.getParentId() == null || departmentVO.getParentId().equals(rootDeptId)) {
            params.put("isTop", true);
        }

        String sr = httpRequestClient.httpPostJson(url,
                JSON.toJSONString(params),
                generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 更新部门
     *
     * @param suiteVO
     * @return
     */
    @Override
    public RsqDepartment updateDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO) {
        JSONObject params = new JSONObject();
        params.put("outerCombineId", generateOuterCombineId(departmentVO));
        params.put("outerCorpId", departmentVO.getCorpId());
        params.put("name", departmentVO.getName());

        params.put("outerParentCombineId", generateOuterCombineId(departmentVO.getCorpId(), departmentVO.getParentId()));
        if (departmentVO.getParentId() == null || departmentVO.getParentId().equals(rootDeptId)) {
            params.put("isTop", true);
        }

        params.put("displayOrder", departmentVO.getOrder());

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("outerId", generateOuterCombineId(departmentVO));
        String url = this.rsqUrlInternal + HttpPathUtil.replacePathVariable(URL_UPDATE_DEPARTMENT, pathParams);

        String sr = httpRequestClient.httpPutJson(url,
                JSON.toJSONString(params),
                generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("updateDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 删除部门
     *
     * @param suiteVO
     * @return
     */
    @Override
    public RsqDepartment deleteDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("outerId", generateOuterCombineId(departmentVO));
        String url = this.rsqUrlInternal + HttpPathUtil.replacePathVariable(URL_DELETE_DEPARTMENT, pathParams);

        String sr = httpRequestClient.doHttpDelete(url, generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("deleteDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 创建新用户
     *
     * @param suiteVO
     * @param staffVO
     * @param corpVO
     * @return
     */
    @Override
    public RsqUser createUser(SuiteVO suiteVO, CorpStaffVO staffVO, CorpVO corpVO) {
        String url = this.rsqUrlInternal + URL_CREATE_USER;

        JSONObject params = new JSONObject();
        params.put("outerCombineId", generateOuterCombineId(staffVO));
        params.put("outerCorpId", corpVO.getCorpId());
        params.put("outerCombineDeptIdList", generateStaffOuterCombineId(staffVO));
        params.put("name", staffVO.getName());
        if (staffVO.getAvatar() != null) {
            params.put("avatar", staffVO.getAvatar());
        }
        if (staffVO.getUnionId() != null) {
            params.put("outerUnionId", staffVO.getUnionId());
        }

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params), generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createUser error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 更新用户
     *
     * @param suiteVO
     * @param staffVO
     * @return
     */
    @Override
    public RsqUser updateUser(SuiteVO suiteVO, CorpStaffVO staffVO) {
        JSONObject params = new JSONObject();
        params.put("outerCombineId", generateOuterCombineId(staffVO));
        params.put("outerCorpId", staffVO.getCorpId());
        if (staffVO.getName() != null) {
            params.put("name", staffVO.getName());
        }
        if (staffVO.getAvatar() != null) {
            params.put("avatar", staffVO.getAvatar());
        }
        if (staffVO.getUnionId() != null) {
            params.put("outerUnionId", staffVO.getUnionId());
        }
        if (staffVO.getDepartment() != null) {
            params.put("outerCombineDeptIdList", generateStaffOuterCombineId(staffVO));
        }

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("outerId", generateOuterCombineId(staffVO));
        String url = this.rsqUrlInternal + HttpPathUtil.replacePathVariable(URL_UPDATE_USER, pathParams);

        String sr = httpRequestClient.httpPutJson(url, JSON.toJSONString(params), generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("updateUser error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 标记用户为管理员
     *
     * @param suiteVO
     * @param staffVO
     * @return
     */
    @Override
    public RsqUser setUserAdmin(SuiteVO suiteVO, CorpStaffVO staffVO) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("outerId", generateOuterCombineId(staffVO));
        pathParams.put("isAdmin", staffVO.getAdmin());
        String url = this.rsqUrlInternal + HttpPathUtil.replacePathVariable(URL_UPDATE_USER_SET_ADMIN, pathParams);

        String sr = httpRequestClient.httpPutJson(url, "", generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("setUserAdmin error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 标记用户为管理员
     *
     * @param suiteVO
     * @param staffVO
     * @return
     */
    @Override
    public void removeUser(SuiteVO suiteVO, CorpStaffVO staffVO) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("outerId", generateOuterCombineId(staffVO));
        String url = this.rsqUrlInternal + HttpPathUtil.replacePathVariable(URL_UPDATE_USER_REMOVE_TEAM, pathParams);

        String sr = httpRequestClient.httpPutJson(url, "", generateHeaderMap(suiteVO));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("removeUser error: " + sr);
        }
    }

    /**
     * 充值
     *
     * @param suiteVO
     * @param pushEventVO
     * @return
     */
    @Override
    public void doCharge(SuiteVO suiteVO, OrderSpecItemVO specItem, OrderRsqPushEventVO pushEventVO) {
        StringBuilder sb = new StringBuilder(getRsqDomain());
        sb.append("/task/v2/tokenAuth/pay/recharge?token=");
        sb.append(suiteVO.getRsqAppToken());
        sb.append("&productName=");
        sb.append(specItem.getRsqProductName());
        sb.append("&userLimit=");
        sb.append(pushEventVO.getQuantity());
        sb.append("&deadLine=");
        // 转成yyyy-MM-dd HH:mm:ss的格式
        try {
            sb.append(URLEncoder.encode(parseFormat(pushEventVO.getServiceStopTime()), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&teamId=");
        sb.append(pushEventVO.getRsqTeamId());

        String url = sb.toString();
        httpRequestClient.doHttpPost(url, "", null);
    }

    private String parseFormat(Long mills) {
        Date date = new Date(mills);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dt.format(date);
    }

    private List<String> generateStaffOuterCombineId(CorpStaffVO corpStaffVO) {
        List<Long> deptList = corpStaffVO.getDepartment();
        String corpId = corpStaffVO.getCorpId();
        List<String> result = new ArrayList<>(deptList.size());
        for (Long deptId : deptList) {
            result.add(generateOuterCombineId(corpId, deptId));
        }
        return result;
    }

    private String generateOuterCombineId(CorpStaffVO corpStaffVO) {
        return corpStaffVO.getCorpId() + "--" + corpStaffVO.getUserId();
    }

    private String generateOuterCombineId(CorpDepartmentVO departmentVO) {
        return departmentVO.getCorpId() + "--" + departmentVO.getDeptId();
    }

    private String generateOuterCombineId(String corpId, Long deptId) {
        return corpId + "--" + deptId;
    }

    private Map<String, String> generateHeaderMap(SuiteVO suiteVO) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", suiteVO.getRsqAppToken());
        return headers;
    }
}
