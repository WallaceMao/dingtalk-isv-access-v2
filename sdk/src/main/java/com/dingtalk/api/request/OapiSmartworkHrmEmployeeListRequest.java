package com.dingtalk.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;
import java.util.List;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.dingtalk.api.DingTalkConstants;
import com.taobao.api.Constants;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.TaobaoUtils;

import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.smartwork.hrm.employee.list request
 * 
 * @author top auto create
 * @since 1.0, 2018.10.11
 */
public class OapiSmartworkHrmEmployeeListRequest extends BaseTaobaoRequest<OapiSmartworkHrmEmployeeListResponse> {
	
	

	/** 
	* 需要获取的花名册字段信息
	 */
	private String fieldFilterList;

	/** 
	* 员工id列表
	 */
	private String useridList;

	public void setFieldFilterList(String fieldFilterList) {
		this.fieldFilterList = fieldFilterList;
	}

	public String getFieldFilterList() {
		return this.fieldFilterList;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	public String getUseridList() {
		return this.useridList;
	}

	public String getApiMethodName() {
		return "dingtalk.oapi.smartwork.hrm.employee.list";
	}

	private String topResponseType = Constants.RESPONSE_TYPE_DINGTALK_OAPI;

     public String getTopResponseType() {
        return this.topResponseType;
     }

     public void setTopResponseType(String topResponseType) {
        this.topResponseType = topResponseType;
     }

     public String getTopApiCallType() {
        return DingTalkConstants.CALL_TYPE_OAPI;
     }

     private String topHttpMethod = DingTalkConstants.HTTP_METHOD_POST;

     public String getTopHttpMethod() {
     	return this.topHttpMethod;
     }

     public void setTopHttpMethod(String topHttpMethod) {
        this.topHttpMethod = topHttpMethod;
     }

     public void setHttpMethod(String httpMethod) {
         this.setTopHttpMethod(httpMethod);
     }

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("field_filter_list", this.fieldFilterList);
		txtParams.put("userid_list", this.useridList);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OapiSmartworkHrmEmployeeListResponse> getResponseClass() {
		return OapiSmartworkHrmEmployeeListResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkMaxListSize(fieldFilterList, 50, "fieldFilterList");
		RequestCheckUtils.checkNotEmpty(useridList, "useridList");
		RequestCheckUtils.checkMaxListSize(useridList, 50, "useridList");
	}
	

}