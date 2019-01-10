package com.rishiqing.dingtalk.converter.suite;

import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDO;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDeptDO;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthUserDO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthDeptVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthUserVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;

/**
 * @author Wallace Mao
 * Date: 2018-12-24 15:02
 */
public class CorpSuiteAuthConverter {
    public static CorpSuiteAuthVO corpSuiteAuthDO2CorpSuiteAuthVO(CorpSuiteAuthDO corpSuiteAuthDO) {
        if (null == corpSuiteAuthDO) {
            return null;
        }
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        corpSuiteAuthVO.setId(corpSuiteAuthDO.getId());
        corpSuiteAuthVO.setGmtCreate(corpSuiteAuthDO.getGmtCreate());
        corpSuiteAuthVO.setGmtModified(corpSuiteAuthDO.getGmtModified());
        corpSuiteAuthVO.setSuiteKey(corpSuiteAuthDO.getSuiteKey());
        corpSuiteAuthVO.setCorpId(corpSuiteAuthDO.getCorpId());
        corpSuiteAuthVO.setPermanentCode(corpSuiteAuthDO.getPermanentCode());
        corpSuiteAuthVO.setChPermanentCode(corpSuiteAuthDO.getChPermanentCode());
        corpSuiteAuthVO.setAuthUserId(corpSuiteAuthDO.getAuthUserId());
        return corpSuiteAuthVO;
    }

    public static CorpSuiteAuthDO corpSuiteAuthVO2CorpSuiteAuthDO(CorpSuiteAuthVO corpSuiteAuthVO) {
        if (null == corpSuiteAuthVO) {
            return null;
        }
        CorpSuiteAuthDO corpSuiteAuthDO = new CorpSuiteAuthDO();
        corpSuiteAuthDO.setId(corpSuiteAuthVO.getId());
        corpSuiteAuthDO.setGmtCreate(corpSuiteAuthVO.getGmtCreate());
        corpSuiteAuthDO.setGmtModified(corpSuiteAuthVO.getGmtModified());
        corpSuiteAuthDO.setSuiteKey(corpSuiteAuthVO.getSuiteKey());
        corpSuiteAuthDO.setCorpId(corpSuiteAuthVO.getCorpId());
        corpSuiteAuthDO.setPermanentCode(corpSuiteAuthVO.getPermanentCode());
        corpSuiteAuthDO.setChPermanentCode(corpSuiteAuthVO.getChPermanentCode());
        corpSuiteAuthDO.setAuthUserId(corpSuiteAuthVO.getAuthUserId());
        return corpSuiteAuthDO;
    }

    public static CorpSuiteAuthDeptVO corpSuiteAuthDeptDO2corpSuiteAuthDeptVO(CorpSuiteAuthDeptDO corpSuiteAuthDeptDO) {
        if (corpSuiteAuthDeptDO == null) {
            return null;
        }
        CorpSuiteAuthDeptVO deptVO = new CorpSuiteAuthDeptVO();
        deptVO.setId(corpSuiteAuthDeptDO.getId());
        deptVO.setGmtCreate(corpSuiteAuthDeptDO.getGmtCreate());
        deptVO.setGmtModified(corpSuiteAuthDeptDO.getGmtModified());
        deptVO.setSuiteKey(corpSuiteAuthDeptDO.getSuiteKey());
        deptVO.setCorpId(corpSuiteAuthDeptDO.getCorpId());
        deptVO.setDeptId(corpSuiteAuthDeptDO.getDeptId());
        return deptVO;
    }

    public static CorpSuiteAuthDeptDO corpSuiteAuthDeptVO2corpSuiteAuthDeptDO(CorpSuiteAuthDeptVO corpSuiteAuthDeptVO) {
        if (corpSuiteAuthDeptVO == null) {
            return null;
        }
        CorpSuiteAuthDeptDO deptDO = new CorpSuiteAuthDeptDO();
        deptDO.setId(corpSuiteAuthDeptVO.getId());
        deptDO.setGmtCreate(corpSuiteAuthDeptVO.getGmtCreate());
        deptDO.setGmtModified(corpSuiteAuthDeptVO.getGmtModified());
        deptDO.setSuiteKey(corpSuiteAuthDeptVO.getSuiteKey());
        deptDO.setCorpId(corpSuiteAuthDeptVO.getCorpId());
        deptDO.setDeptId(corpSuiteAuthDeptVO.getDeptId());
        return deptDO;
    }

    public static CorpSuiteAuthUserVO corpSuiteAuthUserDO2corpSuiteAuthUserVO(CorpSuiteAuthUserDO corpSuiteAuthUserDO) {
        if (corpSuiteAuthUserDO == null) {
            return null;
        }
        CorpSuiteAuthUserVO userVO = new CorpSuiteAuthUserVO();
        userVO.setId(corpSuiteAuthUserDO.getId());
        userVO.setGmtCreate(corpSuiteAuthUserDO.getGmtCreate());
        userVO.setGmtModified(corpSuiteAuthUserDO.getGmtModified());
        userVO.setSuiteKey(corpSuiteAuthUserDO.getSuiteKey());
        userVO.setCorpId(corpSuiteAuthUserDO.getCorpId());
        userVO.setUserId(corpSuiteAuthUserDO.getUserId());
        return userVO;
    }

    public static CorpSuiteAuthUserDO corpSuiteAuthUserVO2corpSuiteAuthUserDO(CorpSuiteAuthUserVO corpSuiteAuthUserVO) {
        if (corpSuiteAuthUserVO == null) {
            return null;
        }
        CorpSuiteAuthUserDO userDO = new CorpSuiteAuthUserDO();
        userDO.setId(corpSuiteAuthUserVO.getId());
        userDO.setGmtCreate(corpSuiteAuthUserVO.getGmtCreate());
        userDO.setGmtModified(corpSuiteAuthUserVO.getGmtModified());
        userDO.setSuiteKey(corpSuiteAuthUserVO.getSuiteKey());
        userDO.setCorpId(corpSuiteAuthUserVO.getCorpId());
        userDO.setUserId(corpSuiteAuthUserVO.getUserId());
        return userDO;
    }
}
