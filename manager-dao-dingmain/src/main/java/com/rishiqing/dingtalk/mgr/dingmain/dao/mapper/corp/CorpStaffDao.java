package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpStaffDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:21
 */
@Repository("corpStaffDao")
public interface CorpStaffDao {
    /**
     * 保持公司用户信息
     * @param corpStaffDO
     */
    void saveOrUpdateCorpStaff(CorpStaffDO corpStaffDO);

    /**
     * 更新第三方应用id的信息
     * @param corpStaffDO
     * @return
     */
    void updateCorpStaffRsqInfo(CorpStaffDO corpStaffDO);

    /**
     * 更新unionId
     * @param corpStaffDO
     * @return
     */
    void updateCorpStaffUnionId(CorpStaffDO corpStaffDO);

    /**
     * 更新用户是否为管理员
     * corpStaffDO.corpId: 必要的
     * corpStaffDO.userId: 必要的
     * corpStaffDO.isAdmin: 必要的
     * corpStaffDO.sysLevel: 必要的
     * @param corpStaffDO
     */
    void updateCorpStaffIsAdmin(CorpStaffDO corpStaffDO);

    /**
     * 根据corpId和userId删除用户
     * @param corpId
     * @param userId
     * @return
     */
    void deleteCorpStaffByCorpIdAndUserId(
            @Param("corpId") String corpId,
            @Param("userId") String userId);

    /**
     * 删除某个企业中scopeVersion小于指定数值的corpStaff记录
     * @param corpId
     * @param lessThanScopeVersion
     */
    void deleteCorpStaffByCorpIdAndScopeVersionLessThan(
            @Param("corpId") String corpId,
            @Param("lessThanScopeVersion") Long lessThanScopeVersion
    );

    /**
     * 根据corpId和userId查询用户
     * @param corpId
     * @param userId
     * @return
     */
    CorpStaffDO getCorpStaffByCorpIdAndUserId(
            @Param("corpId") String corpId,
            @Param("userId") String userId);

    /**
     * 根据corpId查询用户列表
     * @param corpId
     * @return
     */
    List<CorpStaffDO> getCorpStaffListByCorpId(
            @Param("corpId") String corpId);

    /**
     * 根据isAmdin属性获取用户
     * @param isAdmin
     * @return
     */
    List<CorpStaffDO> getCorpStaffListByCorpIdAndIsAdmin(
            @Param("corpId") String corpId,
            @Param("isAdmin") Boolean isAdmin);

    /**
     * 根据corpId查询用户id列表
     * @param corpId
     * @return
     */
    List<String> getCorpStaffUserIdListByCorpId(
            @Param("corpId") String corpId);

    /**
     * 保存用户的删除信息，当用户被删除后，为保存用户与日事清的关联信息，需要将用于移入删除表
     * @param corpStaffDO
     */
    void saveCorpStaffDeleted(CorpStaffDO corpStaffDO);

    List<CorpStaffDO> getPageCorpStaffListByCorpId(
            @Param("corpId") String corpId,
            @Param("limit") Long limit,
            @Param("offset") Long offset);

    /**
     * 根据corpId和userId查询用户
     * @param corpId
     * @param userId
     * @return
     */
    CorpStaffDO getCorpStaffByCorpIdAndUserIdAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("userId") String userId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 根据corpId查询用户列表
     * @param corpId
     * @return
     */
    List<CorpStaffDO> getCorpStaffListByCorpIdAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 根据isAmdin属性获取用户
     * @param isAdmin
     * @return
     */
    List<CorpStaffDO> getCorpStaffListByCorpIdAndIsAdminAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("isAdmin") Boolean isAdmin,
            @Param("scopeVersion") Long scopeVersion
    );

    List<CorpStaffDO> getCorpStaffListByCorpIdAndScopeVersionLessThan(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion
    );

    List<CorpStaffDO> listCorpStaffByCorpIdAndDepartmentLike(
            @Param("corpId") String corpId,
            @Param("likeCondition") String likeCondition
    );

    Long countCorpStaffByCorpId(
            @Param("corpId") String corpId);

//    /**
//     * 根据staff的rsqId获取到userId
//     * @param rsqIds
//     * @return
//     */
//    public List<StaffIdsDO> getUserIdFromRsqId(
//            @Param("corpId") String corpId,
//            @Param("rsqIds") String[] rsqIds);
//    /**
//     * 根据staff的rsqId获取到userId
//     * @param userIds
//     * @return
//     */
//    public List<StaffIdsDO> getRsqIdFromUserId(
//            @Param("corpId") String corpId,
//            @Param("userIds") String[] userIds);


}
