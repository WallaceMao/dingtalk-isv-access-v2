package com.rishiqing.dingtalk.req.rsq.auth.converter;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.rsq.RsqUser;

/**
 * Created by Wallace on 2016/11/29.
 */
public class RsqUserConverter {
    public static RsqUser JSON2RsqUser(JSONObject json){
        RsqUser user = new RsqUser();
        user.setId(json.getLong("id"));
        user.setUsername(json.getString("username"));
        if(json.containsKey("password")){
            user.setPassword(json.getString("password"));
        }
        if(json.containsKey("outerId")){
            user.setOuterId(json.getString("outerId"));
        }
        if(json.containsKey("realName")){
            user.setRealName(json.getString("realName"));
        }
        if(json.containsKey("fromClient")){
            user.setFromClient(json.getString("fromClient"));
        }
        if(json.containsKey("unionId")){
            user.setUnionId(json.getString("unionId"));
        }
        if(json.containsKey("loginToken")){
            user.setLoginToken(json.getString("loginToken"));
        }
        return user;
    }
}
