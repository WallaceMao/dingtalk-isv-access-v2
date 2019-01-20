package com.rishiqing.dingtalk.svc.converter.suite;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:28
 */
public class CorpSuiteAuthConverter {
    public static CorpSuiteAuthVO corpAuthInfoVO2CorpSuiteAuthVO(String suiteKey, CorpAuthInfoVO corpAuthInfo) {
        if (corpAuthInfo == null
                || corpAuthInfo.getAuthCorpInfo() == null
                || corpAuthInfo.getAuthUserInfo() == null) {
            return null;
        }
        CorpAuthInfoVO.AuthUserInfo userInfo = corpAuthInfo.getAuthUserInfo();
        CorpAuthInfoVO.AuthCorpInfo corpInfo = corpAuthInfo.getAuthCorpInfo();

        CorpSuiteAuthVO corpSuite = new CorpSuiteAuthVO();
        corpSuite.setSuiteKey(suiteKey);
        corpSuite.setCorpId(corpInfo.getCorpId());
        corpSuite.setAuthUserId(userInfo.getUserId());
        corpSuite.setPermanentCode(corpAuthInfo.getPermanentCode());
        corpSuite.setChPermanentCode(corpAuthInfo.getChPermanentCode());

        if (corpAuthInfo.getAuthScope() != null
                && corpAuthInfo.getAuthScope().getAuthOrgScopes() != null) {
            CorpAuthScopeInfoVO authScope = corpAuthInfo.getAuthScope();
            CorpAuthScopeInfoVO.AuthOrgScopes scopes = authScope.getAuthOrgScopes();
            List<Long> deptIdList = scopes.getAuthedDept() == null
                    ? new ArrayList<Long>() : scopes.getAuthedDept();
            List<String> userIdList = scopes.getAuthedUser() == null
                    ? new ArrayList<String>() : scopes.getAuthedUser();
            corpSuite.setScopeAuthedDeptIdList(deptIdList);
            corpSuite.setScopeAuthedUserIdList(userIdList);
        }

        return corpSuite;
    }

    public static CorpAuthInfoVO corpId2CorpAuthInfoVO(String corpId) {
        if (corpId == null) {
            return null;
        }
        CorpAuthInfoVO corpAuthInfo = new CorpAuthInfoVO();
        CorpAuthInfoVO.AuthCorpInfo corpInfo = new CorpAuthInfoVO.AuthCorpInfo();
        corpInfo.setCorpId(corpId);
        corpAuthInfo.setAuthCorpInfo(corpInfo);
        return corpAuthInfo;
    }
}
