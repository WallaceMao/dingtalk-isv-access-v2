package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:35
 */
public class CorpDepartmentManageServiceImpl implements CorpDepartmentManageService {
    @Autowired
    private CorpDepartmentDao corpDepartmentDao;

    public CorpDepartmentVO getCorpDepartmentByCorpIdAndDeptId(String corpId, Long deptId){
        return null;
    }
}
