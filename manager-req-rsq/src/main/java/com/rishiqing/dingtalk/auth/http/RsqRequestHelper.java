package com.rishiqing.dingtalk.auth.http;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.self.api.model.RsqCorp;
import com.rishiqing.self.api.model.RsqDepartment;
import com.rishiqing.self.api.model.RsqUser;

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
