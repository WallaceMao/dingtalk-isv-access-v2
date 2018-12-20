package com.rishiqing.dingtalk.biz.service.base.corp.impl;

import com.rishiqing.dingtalk.biz.converter.corp.*;
import com.rishiqing.dingtalk.biz.enmutype.CorpLockType;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.biz.service.util.CorpLockService;
import com.rishiqing.dingtalk.dao.mapper.corp.*;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpAppDao;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthDao;
import com.rishiqing.dingtalk.dao.model.corp.*;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDO;
import com.rishiqing.dingtalk.isv.api.model.corp.*;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:56
 */
public class CorpManageServiceImpl implements CorpManageService {
    @Autowired
    private CorpDao corpDao;
    @Autowired
    private CorpSuiteAuthDao corpSuiteAuthDao;
    @Autowired
    private CorpStaffDao corpStaffDao;
    @Autowired
    private CorpTokenDao corpTokenDao;
    @Autowired
    private CorpJSAPITicketDao corpJSAPITicketDao;
    @Autowired
    private CorpChargeStatusDao corpChargeStatusDao;
    @Autowired
    private CorpLockService corpLockService;
    @Autowired
    private SuiteRequestHelper suiteTopRequestHelper;
    @Autowired
    private CorpAppDao corpAppDao;
    @Autowired
    private CorpStatisticDao corpStatisticDao;

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO){
        corpDao.saveOrUpdateCorp(
                CorpConverter.CorpVO2CorpDO(corpVO)
        );
    }

    @Override
    public void saveOrUpdateCorpJSTicket(CorpJSAPITicketVO corpJSAPITicketVO){
        corpJSAPITicketDao.saveOrUpdateCorpJSAPITicket(
                CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(corpJSAPITicketVO)
        );
    }

    @Override
    public CorpDO getCorpById(Long id){
        return corpDao.getCorpById(id);
    }

    @Override
    public CorpVO getCorpByCorpId(String corpId) {
        return CorpConverter.CorpDO2CorpVO(
                corpDao.getCorpByCorpId(corpId)
        );
    }

    @Override
    public void updateRsqInfo(CorpVO corpVO) {
        corpDao.updateCorpRsqInfo(
                CorpConverter.CorpVO2CorpDO(corpVO)
        );
    }

    @Override
    public CorpTokenVO getCorpTokenByCorpId(String corpId){
        CorpTokenDO corpTokenDO = corpTokenDao.getCorpTokenByCorpId(corpId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新corptoken
        //如果过期重新请求
        if (null == corpTokenDO || calendar.getTime().compareTo(corpTokenDO.getExpiredTime()) > 0) {
            CorpLockDO lock = corpLockService.requireLock(corpId, CorpLockType.TOKEN);
            //  如果获取到锁，则更新token
            if(null != lock){
                CorpTokenVO corpTokenVO = suiteTopRequestHelper.getCorpToken(corpId);
                corpTokenDO = CorpTokenConverter.CorpTokenVO2CorpTokenDO(corpTokenVO);
                corpTokenDao.saveOrUpdateCorpToken(corpTokenDO);

                //  释放锁
                corpLockService.releaseLock(corpId, CorpLockType.TOKEN);
            }

        }
        return CorpTokenConverter.CorpTokenDO2CorpTokenVO(corpTokenDO);
    }

    @Override
    public CorpJSAPITicketVO getCorpJSAPITicketByCorpId(String corpId) {
        CorpJSAPITicketDO corpJSTicketDO = corpJSAPITicketDao.getCorpJSAPITicketByCorpId(corpId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);//为了防止误差,提前10分钟更新jsticket
        //TODO 需要做并发控制
        if (null == corpJSTicketDO || calendar.getTime().compareTo(corpJSTicketDO.getExpiredTime()) > 0) {
            CorpLockDO lock = corpLockService.requireLock(corpId, CorpLockType.JSAPI_TICKET);
            //  如果获取到锁，则更新token
            if(null != lock){
                CorpTokenVO corpTokenVO = this.getCorpTokenByCorpId(corpId);
                CorpJSAPITicketVO corpJSTicketVO = suiteTopRequestHelper.getCorpJSAPITicket(corpTokenVO);
                corpJSTicketDO = CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(corpJSTicketVO);
                this.saveOrUpdateCorpJSTicket(corpJSTicketVO);

                corpLockService.releaseLock(corpId, CorpLockType.JSAPI_TICKET);
            }
        }
        return CorpJSAPITicketConverter.corpJSTicketDO2CorpJSTicketVO(corpJSTicketDO);
    }

    @Override
    public CorpChargeStatusVO getCorpChargeStatusByCorpId(String corpId){
        return CorpChargeStatusConverter.corpChargeStatusDO2CorpChargeStatusVO(
                corpChargeStatusDao.getCorpChargeStatusByCorpId(corpId)
        );
    }

    /**
     * 找到一个用户作为团队创建者，按照如下逻辑查找：
     * 1. 理想情况下：首先查找corp的corpSuiteAuth表中的authUserId字段，如果该字段不为空，且可以根据userId查找到该用户，那么就使用这个用户
     * 2. 如果1不满足，那么查找数据库中corp中的一个管理员，作为团队创建者
     * 3. 如果2仍然找不到，那么就找id最小的一个用户作为创建者
     * @param corpId
     * @return
     */
    @Override
    public CorpStaffVO findATeamCreator(String corpId){
        CorpSuiteAuthDO corpSuiteAuthDO = corpSuiteAuthDao.getCorpSuiteAuthByCorpId(corpId);
        if(corpSuiteAuthDO.getAuthUserId() != null){
            CorpStaffDO corpStaffDO = corpStaffDao.getCorpStaffByCorpIdAndUserId(corpId, corpSuiteAuthDO.getAuthUserId());
            if(null != corpStaffDO){
                return CorpStaffConverter.corpStaffDO2CorpStaffVO(corpStaffDO);
            }
        }
        List<CorpStaffDO> adminList = corpStaffDao.getCorpStaffListByCorpIdAndIsAdmin(corpId, true);
        if(adminList != null && adminList.size() > 0){
            return CorpStaffConverter.corpStaffDO2CorpStaffVO(adminList.get(0));
        }
        List<CorpStaffDO> firstCommonUser = corpStaffDao.getPageCorpStaffListByCorpId(corpId, 1L, 0L);
        if(firstCommonUser != null && firstCommonUser.size() > 0){
            return CorpStaffConverter.corpStaffDO2CorpStaffVO(firstCommonUser.get(0));
        }
        return null;
    }

    @Override
    public void deleteCorpSuiteAuthByCorpId(String corpId){
        corpSuiteAuthDao.deleteCorpSuiteAuthByCorpId(corpId);
    }

    @Override
    public void deleteCorpAppByCorpId(String corpId, Long appId){
        corpAppDao.deleteCorpAppByCorpIdAndAppId(corpId, appId);
    }

    @Override
    public void deleteCorpTokenByCorpId(String corpId){
        corpTokenDao.deleteCorpTokenByCorpId(corpId);
    }

    @Override
    public void deleteCorpJSAPITicketByCorpId(String corpId){
        corpJSAPITicketDao.deleteCorpJSAPITicketByCorpId(corpId);
    }

    @Override
    public void saveOrUpdateCorpStatisticUserCount(String corpId, Long userCount) {
        CorpStatisticDO corpStatisticDO = corpStatisticDao.getCorpStatisticByCorpId(corpId);
        if(corpStatisticDO == null) {
            corpStatisticDO = new CorpStatisticDO();
            corpStatisticDO.setCorpId(corpId);
        }
        corpStatisticDO.setStaffCount(userCount);
        corpStatisticDao.saveOrUpdateCorpStatistic(corpStatisticDO);
    }

    @Override
    public void saveOrUpdateCorpStatistic(CorpStatisticVO corpStatisticVO) {
        corpStatisticDao.saveOrUpdateCorpStatistic(
                CorpStatisticConverter.corpStatisticVO2CorpStatisticDO(corpStatisticVO)
        );
    }

    @Override
    public CorpStatisticVO getCorpStatisticByCorpId(String corpId) {
        return CorpStatisticConverter.corpStatisticDO2CorpStatisticVO(
                corpStatisticDao.getCorpStatisticByCorpId(corpId)
        );
    }

    @Override
    public CorpStatisticVO getCorpStatisticByCorpIdForUpdate(String corpId) {
        return CorpStatisticConverter.corpStatisticDO2CorpStatisticVO(
                corpStatisticDao.getCorpStatisticByCorpIdForUpdate(corpId)
        );
    }

    @Override
    public List<CorpDO> listPageCorpWithCreator(Long pageSize, Long offset) {
        return corpDao.listPageCorpWithCreator(pageSize, offset);
    }

    @Override
    public Long countCorp() {
        return corpDao.countCorp();
    }

    @Override
    public Long countCorpSuiteAuth() {
        return corpDao.countCorpSuiteAuth();
    }
}
