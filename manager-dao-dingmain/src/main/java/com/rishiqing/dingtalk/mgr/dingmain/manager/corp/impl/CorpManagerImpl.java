package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.domain.corp.*;
import com.rishiqing.dingtalk.api.model.vo.corp.*;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.mgr.dingmain.constant.CorpLockType;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.*;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.CorpAppDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.CorpSuiteAuthDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.SuiteDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.SuiteTicketDao;
import com.rishiqing.dingtalk.api.model.domain.suite.CorpSuiteAuthDO;
import com.rishiqing.dingtalk.api.model.domain.suite.SuiteDO;
import com.rishiqing.dingtalk.api.model.domain.suite.SuiteTicketDO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.converter.corp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:56
 */
public class CorpManagerImpl implements CorpManager {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpManagerImpl.class);

    @Autowired
    private SuiteDao suiteDao;
    @Autowired
    private CorpDao corpDao;
    @Autowired
    private SuiteTicketDao suiteTicketDao;
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
    private CorpLockDao corpLockDao;
    @Autowired
    private CorpAppDao corpAppDao;
    @Autowired
    private CorpStatisticDao corpStatisticDao;
    @Autowired
    private SuiteRequestHelper suiteRequestHelper;
    @Autowired
    private CorpSyncFilterDao corpSyncFilterDao;
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
    public List<String> listCorpCorpIdByCreateTimeBetween(Date startDate, Date endDate) {
        return corpDao.listCorpCorpIdByGmtCreateBetween(startDate, endDate);
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
            CorpLockDO lock = requireLock(corpId, CorpLockType.TOKEN);
            //  如果获取到锁，则更新token
            if(null != lock){
                SuiteDO suiteDO = suiteDao.getSuite();
                SuiteTicketDO suiteTicketDO = suiteTicketDao.getSuiteTicket();

                CorpTokenVO corpTokenVO = suiteRequestHelper.getCorpToken(
                        suiteDO.getSuiteKey(), suiteDO.getSuiteSecret(), suiteTicketDO.getSuiteTicket(), corpId);
                corpTokenDO = CorpTokenConverter.CorpTokenVO2CorpTokenDO(corpTokenVO);
                corpTokenDao.saveOrUpdateCorpToken(corpTokenDO);

                //  释放锁
                releaseLock(corpId, CorpLockType.TOKEN);
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
            CorpLockDO lock = requireLock(corpId, CorpLockType.JSAPI_TICKET);
            //  如果获取到锁，则更新token
            if(null != lock){
                CorpTokenVO corpTokenVO = this.getCorpTokenByCorpId(corpId);
                CorpJSAPITicketVO corpJSTicketVO = suiteRequestHelper.getCorpJSAPITicket(corpTokenVO);
                corpJSTicketDO = CorpJSAPITicketConverter.corpJSTicketVO2CorpJSTicketDO(corpJSTicketVO);
                this.saveOrUpdateCorpJSTicket(corpJSTicketVO);

                releaseLock(corpId, CorpLockType.JSAPI_TICKET);
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
    public List<CorpDO> listPageCorpWithCreator(Long pageSize, Long offset, Map<String, Object> clause) {
        return corpDao.listPageCorpWithCreator(pageSize, offset, clause);
    }

    @Override
    public Long countCorp() {
        return corpDao.countCorp();
    }

    @Override
    public Long countCorpSuiteAuth(Map<String, Object> clause) {
        return corpDao.countCorpSuiteAuth(clause);
    }

    /**
     * 请求锁，事务控制，基本逻辑如下：
     * 1  根据corpId和lockType读取lock
     * 2  如果lock不存在，则说明是首次获取，可直接获取到该锁，并将锁的下次超时时间更新为当前时间加超时阀值
     * 3  查看lock是否超时。如果超时，则表示可以获取到该锁，并将锁的下次超时时间更新为当前时间加超时阀值
     * 4  如果lock未超时，说明该锁已被占用，则获取锁失败。
     * 5  释放锁时，更新lock的超时时间为当前时间，锁可有其他请求获取。
     * @return
     */
    @Override
    @Transactional
    public CorpLockDO requireLock(String corpId, CorpLockType lockType) {
        CorpLockDO corpLock;
        String lockKey = corpId + "_" + lockType.getKey();
        //  设置超时时间默认为1分钟
        int offset = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        calendar.add(Calendar.MINUTE, offset);
        Date date = calendar.getTime();

        corpLock = corpLockDao.getCorpLockByLockKey(lockKey);
        //  如果不存在，则直接保存
        if(null == corpLock){
            corpLock = new CorpLockDO();
            corpLock.setLockKey(lockKey);
            corpLock.setExpire(date);
        } else {
            //  如果lock的期限已到，则重新设置期限
            if(corpLock.getExpire().compareTo(new Date()) <= 0){
                corpLock.setExpire(date);
            }else{
                //  如果lock的期限未到，说明锁已被占用，则返回null，表示请求锁失败
                return null;
            }
        }
        corpLockDao.saveOrUpdateCorpLock(corpLock);
        return corpLock;
    }

    /**
     * 释放锁时，更新lock的超时时间为当前时间，锁可有其他请求获取。
     * @param corpId
     * @param lockType
     * @return
     */
    @Override
    @Transactional
    public CorpLockDO releaseLock(String corpId, CorpLockType lockType) {
        CorpLockDO corpLock;
        String lockKey = corpId + "_" + lockType.getKey();

        corpLock = corpLockDao.getCorpLockByLockKey(lockKey);
        //  如果不存在，则直接保存
        if(null == corpLock){
            bizLogger.warn(LogFormatter.format(
                    LogFormatter.LogEvent.END,
                    "释放锁异常：锁不存在",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("lockType", lockType)
            ));
        } else {
            corpLock.setExpire(new Date());
        }
        corpLockDao.saveOrUpdateCorpLock(corpLock);
        return corpLock;
    }

    @Override
    public List<CorpDO> listCorpBetweenDate(Date startDate, Date endDate) {
        return corpDao.listCorpBetweenDate(startDate,endDate);
    }

    @Override
    public void saveOrUpdateCorpSyncFilter(CorpSyncFilterDO corpSyncFilterDO) {
        corpSyncFilterDao.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
    }
}
