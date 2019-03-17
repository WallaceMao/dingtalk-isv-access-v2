package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.util;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.mgr.dingmain.constant.ManagerDaoDingmainConstant;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-01-26 17:20
 */
public class TestDepartmentFactory {
    //  延迟1ms，保证每次的创建的时间戳不一样
    private static final Long CREATE_DELAY_MILLS = 1L;

    public static CorpDepartmentVO getRootCorpDepartmentVO(String corpId, Long scopeVersion) throws Exception {
        //  暂停1ms保证
        Thread.sleep(CREATE_DELAY_MILLS);
        CorpDepartmentVO deptVO = getCorpDepartmentVO(corpId, null, scopeVersion);
        deptVO.setDeptId(ManagerDaoDingmainConstant.DEPT_ID_ROOT);
        return deptVO;
    }
    public static CorpDepartmentVO getCorpDepartmentVO(String corpId, Long parentDeptId, Long scopeVersion) throws Exception {
        Thread.sleep(CREATE_DELAY_MILLS);
        CorpDepartmentVO corpDepartmentVO = new CorpDepartmentVO();
        Long mills = new Date().getTime();
        corpDepartmentVO.setCorpId(corpId);
        corpDepartmentVO.setName("auto_test_dept_name_" + mills);
        corpDepartmentVO.setOrder(mills);
        corpDepartmentVO.setDeptId(mills);
        corpDepartmentVO.setScopeVersion(scopeVersion);
        corpDepartmentVO.setParentId(parentDeptId);
        return corpDepartmentVO;
    }
}
