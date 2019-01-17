package com.rishiqing.dingtalk.api.model.vo.corp;

import java.util.List;

/**
 * 通讯录权限范围的对象
 * @author Wallace Mao
 * Date: 2018-11-06 15:47
 */
public class CorpAuthScopeInfoVO {
    private Long errcode;
    private String errmsg;
    private List<String> conditionField;
    private List<String> authUserField;
    private CorpAuthScopeInfoVO.AuthOrgScopes authOrgScopes;

    public static class AuthOrgScopes {
        private List<String> authedUser;
        private List<Long> authedDept;

        public List<String> getAuthedUser() {
            return authedUser;
        }

        public void setAuthedUser(List<String> authedUser) {
            this.authedUser = authedUser;
        }

        public List<Long> getAuthedDept() {
            return authedDept;
        }

        public void setAuthedDept(List<Long> authedDept) {
            this.authedDept = authedDept;
        }
    }

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<String> getConditionField() {
        return conditionField;
    }

    public void setConditionField(List<String> conditionField) {
        this.conditionField = conditionField;
    }

    public List<String> getAuthUserField() {
        return authUserField;
    }

    public void setAuthUserField(List<String> authUserField) {
        this.authUserField = authUserField;
    }

    public AuthOrgScopes getAuthOrgScopes() {
        return authOrgScopes;
    }

    public void setAuthOrgScopes(AuthOrgScopes authOrgScopes) {
        this.authOrgScopes = authOrgScopes;
    }
}
