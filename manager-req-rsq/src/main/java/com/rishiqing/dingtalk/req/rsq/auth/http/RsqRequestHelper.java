package com.rishiqing.dingtalk.req.rsq.auth.http;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.api.model.rsq.RsqCorp;
import com.rishiqing.dingtalk.api.model.rsq.RsqDepartment;
import com.rishiqing.dingtalk.api.model.rsq.RsqUser;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 21:18
 */
public interface RsqRequestHelper {
    RsqCorp createCorp(SuiteVO suiteVO, CorpVO corpVO, CorpStaffVO creator);

    RsqDepartment createDepartment(SuiteVO suiteVO, CorpVO corpVO, CorpDepartmentVO departmentVO, CorpDepartmentVO parentCorpDepartmentVO);

    RsqDepartment updateDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO);

    RsqDepartment deleteDepartment(SuiteVO suiteVO, CorpDepartmentVO departmentVO);

    RsqUser createUser(SuiteVO suiteVO, CorpStaffVO staffVO, CorpVO corpVO);

    RsqUser updateUser(SuiteVO suiteVO, CorpStaffVO staffVO);

    RsqUser setUserAdmin(SuiteVO suiteVO, CorpStaffVO staffVO);

    void removeUser(SuiteVO suiteVO, CorpStaffVO staffVO);

    void doCharge(SuiteVO suiteVO, OrderSpecItemVO specItem, OrderRsqPushEventVO pushEventVO);
}
