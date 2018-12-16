package com.rishiqing.dingtalk.biz.service.base.suite.impl;

import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthDeptDao;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthUserDao;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDeptDO;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthUserDO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthDeptVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthUserVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.biz.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:26
 */
public class CorpSuiteAuthManageServiceImpl implements CorpSuiteAuthManageService {
    @Autowired
    private CorpSuiteAuthDao corpSuiteAuthDao;
    @Autowired
    private CorpSuiteAuthDeptDao corpSuiteAuthDeptDao;
    @Autowired
    private CorpSuiteAuthUserDao corpSuiteAuthUserDao;

    /**
     * 保存corpSuiteAuth的基本信息以及corpSuiteAuthDept和corpSuiteAuthUser的信息
     * @param corpSuiteAuthVO
     */
    @Override
    public void saveOrUpdateCorpSuiteAuth(CorpSuiteAuthVO corpSuiteAuthVO) {
        corpSuiteAuthDao.saveOrUpdateCorpSuiteAuth(
                CorpSuiteAuthConverter.corpSuiteAuthVO2CorpSuiteAuthDO(corpSuiteAuthVO)
        );
        String suiteKey = corpSuiteAuthVO.getSuiteKey();
        String corpId = corpSuiteAuthVO.getCorpId();
        List<Long> deptIdList = corpSuiteAuthVO.getScopeAuthedDeptIdList();
        if (deptIdList != null && deptIdList.size() > 0) {
            deleteCorpSuiteAuthDeptByCorpId(corpId);
            saveBatchCorpSuiteAuthDept(suiteKey, corpId, deptIdList);
        }
        List<String> userIdList = corpSuiteAuthVO.getScopeAuthedUserIdList();
        if (userIdList != null && userIdList.size() > 0) {
            deleteCorpSuiteAuthUserByCorpId(corpId);
            saveBatchCorpSuiteAuthUser(suiteKey, corpId, userIdList);
        }
    }

    @Override
    public CorpSuiteAuthVO getCorpSuiteAuth(String corpId){
        return CorpSuiteAuthConverter.corpSuiteAuthDO2CorpSuiteAuthVO(
                corpSuiteAuthDao.getCorpSuiteAuthByCorpId(corpId)
        );
    }

    @Override
    public CorpSuiteAuthDeptVO getCorpSuiteAuthDeptByCorpIdAndDeptId(
            String corpId, Long deptId) {
        return CorpSuiteAuthConverter.corpSuiteAuthDeptDO2corpSuiteAuthDeptVO(
                corpSuiteAuthDeptDao.getCorpSuiteAuthDeptByCorpIdAndDeptId(corpId, deptId)
        );
    }

    @Override
    public CorpSuiteAuthUserVO getCorpSuiteAuthUserByCorpIdAndUserId(
            String corpId, String userId) {
        return CorpSuiteAuthConverter.corpSuiteAuthUserDO2corpSuiteAuthUserVO(
                corpSuiteAuthUserDao.getCorpSuiteAuthUserByCorpIdAndUserId(corpId, userId)
        );
    }

    private void saveBatchCorpSuiteAuthDept(String suiteKey, String corpId, List<Long> corpSuiteAuthDeptIdList) {
        List<CorpSuiteAuthDeptDO> deptDOList = new ArrayList<>(corpSuiteAuthDeptIdList.size());
        for (Long deptId : corpSuiteAuthDeptIdList) {
            CorpSuiteAuthDeptDO authDO = new CorpSuiteAuthDeptDO();
            authDO.setSuiteKey(suiteKey);
            authDO.setCorpId(corpId);
            authDO.setDeptId(String.valueOf(deptId));
            deptDOList.add(authDO);
        }
        corpSuiteAuthDeptDao.saveBatchCorpSuiteAuthDept(deptDOList);
    }

    private void deleteCorpSuiteAuthDeptByCorpId(String corpId) {
        corpSuiteAuthDeptDao.deleteCorpSuiteAuthDeptByCorpId(corpId);
    }

    private void saveBatchCorpSuiteAuthUser(String suiteKey, String corpId, List<String> corpSuiteAuthUserIdList) {
        List<CorpSuiteAuthUserDO> userDOList = new ArrayList<>(corpSuiteAuthUserIdList.size());
        for (String userId : corpSuiteAuthUserIdList) {
            CorpSuiteAuthUserDO authDO = new CorpSuiteAuthUserDO();
            authDO.setSuiteKey(suiteKey);
            authDO.setCorpId(corpId);
            authDO.setUserId(userId);
            userDOList.add(authDO);
        }
        corpSuiteAuthUserDao.saveBatchCorpSuiteAuthUser(userDOList);
    }

    private void deleteCorpSuiteAuthUserByCorpId(String corpId) {
        corpSuiteAuthUserDao.deleteCorpSuiteAuthUserByCorpId(corpId);
    }
}
