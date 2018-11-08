package com.rishiqing.self.biz.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.exception.HttpRequestException;
import com.rishiqing.dingtalk.biz.http.HttpRequestClient;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.self.api.model.RsqCorp;
import com.rishiqing.self.api.model.RsqDepartment;
import com.rishiqing.self.api.model.RsqUser;
import com.rishiqing.self.biz.converter.RsqCorpConverter;
import com.rishiqing.self.biz.converter.RsqDepartmentConverter;
import com.rishiqing.self.biz.converter.RsqUserConverter;
import com.rishiqing.self.biz.http.RsqRequestHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 与日事清服务器发送公司和账号创建请求的helper
 * Created by Wallace on 2016/11/19.
 */
public class RsqApiRequestHelper implements RsqRequestHelper {
    private HttpRequestClient httpRequestClient;
    private String rsqDomain;

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

    /**
     * 创建公司
     * @param suiteVO
     * @param corpVO
     * @param creator
     * @return
     */
    @Override
    public RsqCorp createCorp(SuiteVO suiteVO, CorpVO corpVO, CorpStaffVO creator){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/createTeam?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("appName", suiteVO.getRsqAppName());
        params.put("name", corpVO.getCorpName());
        params.put("outerId", corpVO.getCorpId());
        params.put("note", String.valueOf(corpVO.getId()));
        if(creator != null){
            JSONObject creatorMap = new JSONObject();
            creatorMap.put("username", creator.getRsqUsername());
            creatorMap.put("password", creator.getRsqPassword());
            creatorMap.put("realName", creator.getName());
            creatorMap.put("outerId", creator.getCorpId() + "--" + creator.getUserId());
            creatorMap.put("unionId", creator.getUnionId());
            params.put("creator", creatorMap);
        }
        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createCorp error: " + sr);
        }
        return RsqCorpConverter.JSON2RsqCorp(jsonObject);
    }

    /**
     * 创建部门
     * @param suiteVO
     * @param corpVO
     * @return
     */
    @Override
    public RsqDepartment createDepartment(SuiteVO suiteVO, CorpVO corpVO, CorpDepartmentVO departmentVO, CorpDepartmentVO parentCorpDepartmentVO){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/createDepartment?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("name", departmentVO.getName());
        params.put("teamId", corpVO.getRsqId());
        params.put("orderNum", departmentVO.getOrder());

        params.put("outerId", departmentVO.getCorpId() + "--" + departmentVO.getDeptId());

        String parentId = "0";
        if(null != parentCorpDepartmentVO){
            parentId = parentCorpDepartmentVO.getRsqId();
        }
        params.put("parentId", parentId);

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 更新部门
     * @param suiteVO
     * @return
     */
    @Override
    public RsqDepartment updateDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO, CorpDepartmentVO parentCorpDepartmentVO){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/updateDepartment?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("id", departmentVO.getRsqId());
        params.put("name", departmentVO.getName());

        String parentId = "0";
        if(null != parentCorpDepartmentVO){
            parentId = parentCorpDepartmentVO.getRsqId();
        }
        params.put("parentId", parentId);

        params.put("orderNum", departmentVO.getOrder());

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("updateDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 删除部门
     * @param suiteVO
     * @return
     */
    @Override
    public RsqDepartment deleteDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/deleteDepartment?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("id", departmentVO.getRsqId());

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("deleteDepartment error: " + sr);
        }

        return RsqDepartmentConverter.JSON2RsqDepartment(jsonObject);
    }

    /**
     * 创建新用户
     * @param suiteVO
     * @param staffVO
     * @param corpVO
     * @param others 需要在others的map中包括一个"rsqDepartment"的key值，表示日事清应用中用户所属的部门id的列表
     * @return
     */
    @Override
    public RsqUser createUser(SuiteVO suiteVO, CorpStaffVO staffVO, CorpVO corpVO, Map others){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/createUser?token=" + suiteVO.getRsqAppToken();

        JSONObject params = new JSONObject();
        params.put("appName", suiteVO.getRsqAppName());
        params.put("username", staffVO.getRsqUsername());
        params.put("password", staffVO.getRsqPassword());
        params.put("realName", staffVO.getName());
        params.put("outerId", corpVO.getCorpId() + "--" + staffVO.getUserId());
        params.put("teamId", corpVO.getRsqId());
        params.put("unionId", staffVO.getUnionId());
        params.put("department", others.get("rsqDepartment"));

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("createUser error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 更新用户
     * @param suiteVO
     * @param staffVO
     * @param others 如果用户的部门信息有更新，需要在others的map中包括一个"rsqDepartment"的key值
     * @return
     */
    @Override
    public RsqUser updateUser(SuiteVO suiteVO, CorpStaffVO staffVO, Map others){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/updateUser?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("id", staffVO.getRsqUserId());
        params.put("realName", staffVO.getName());
        params.put("unionId", staffVO.getUnionId());

        if(others.containsKey("rsqDepartment")){
            params.put("department", others.get("rsqDepartment"));
        }

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("updateUser error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 标记用户为管理员
     * @param suiteVO
     * @param staffVO
     * @return
     */
    @Override
    public RsqUser setUserAdmin(SuiteVO suiteVO, CorpStaffVO staffVO){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/userSetAdmin?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("id", staffVO.getRsqUserId());
        params.put("isAdmin", staffVO.getAdmin());

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("setUserAdmin error: " + sr);
        }

        return RsqUserConverter.JSON2RsqUser(jsonObject);
    }

    /**
     * 标记用户为管理员
     * @param suiteVO
     * @param staffVO
     * @return
     */
    @Override
    public void removeUser(SuiteVO suiteVO, CorpStaffVO staffVO){
        String url = getRsqDomain() + "/task/v2/tokenAuth/autoCreate/userLeaveTeam?token=" + suiteVO.getRsqAppToken();
        JSONObject params = new JSONObject();
        params.put("id", staffVO.getRsqUserId());

        String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(params));
        JSONObject jsonObject = JSON.parseObject(sr);

        if (jsonObject.containsKey("errcode") && 0 != jsonObject.getLong("errcode")) {
            throw new HttpRequestException("removeUser error: " + sr);
        }
    }

    /**
     * 充值
     * @param suiteVO
     * @param pushEventVO
     * @return
     */
    @Override
    public void doCharge(SuiteVO suiteVO, OrderSpecItemVO specItem, OrderRsqPushEventVO pushEventVO){
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

    private String parseFormat(Long mills){
        Date date = new Date(mills);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dt.format(date);
    }
}
