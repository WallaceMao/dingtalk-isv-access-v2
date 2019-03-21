package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpDepartmentDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:13
 */
@Repository("corpDepartmentDao")
public interface CorpDepartmentDao {
    /**
     * 保持公司部门信息
     * @param corpDepartmentDO
     */
    void saveOrUpdateCorpDepartment(CorpDepartmentDO corpDepartmentDO);

    /**
     * 更新第三方应用id信息
     * @param corpDepartmentDO
     * @return
     */
    void updateCorpDepartmentRsqInfo(CorpDepartmentDO corpDepartmentDO);

    /**
     * 根据corpId和deptId删除部门
     * @param corpId
     * @param deptId
     * @return
     */
    void deleteCorpDepartmentByCorpIdAndDeptId(
            @Param("corpId") String corpId,
            @Param("deptId") Long deptId);

    void deleteCorpDepartmentByCorpIdAndScopeVersionLessThan(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 根据corpId和deptId查询部门
     * @param corpId
     * @param deptId
     * @return
     */
    CorpDepartmentDO getCorpDepartmentByCorpIdAndDeptId(
            @Param("corpId") String corpId,
            @Param("deptId") Long deptId);

    /**
     * 根据corpId查询部门列表
     * @param corpId
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByCorpId(
            @Param("corpId") String corpId);

    /**
     * 获取有限数量的部门
     * @param corpId
     * @param limit
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByCorpIdLimit(
            @Param("corpId") String corpId,
            @Param("limit") Long limit);

    /**
     * 根据corpId查询部门列表
     * @param parentId
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByParentId(
            @Param("corpId") String corpId,
            @Param("parentId") Long parentId);

    /**
     * 只获取id
     * @param corpId
     * @param parentId
     * @return
     */
    List<Long> listCorpDepartmentDeptIdByCorpIdAndParentId(
            @Param("corpId") String corpId,
            @Param("parentId") Long parentId);

    /**
     * 根据corpId和deptId查询部门
     * @param corpId
     * @param deptId
     * @return
     */
    CorpDepartmentDO getCorpDepartmentByCorpIdAndDeptIdAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("deptId") Long deptId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 根据corpId查询scopeVersion小于scopeVersion的部门列表
     * @param corpId
     * @param scopeVersion
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByCorpIdAndScopeVersionLessThan(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 根据corpId查询部门列表，scope版，只获取指定scope中的
     * @param corpId
     * @param scopeVersion
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByCorpIdAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion
    );

    /**
     * 获取有限数量的部门
     * @param corpId
     * @param scopeVersion
     * @param limit
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByCorpIdAndScopeVersionLimit(
            @Param("corpId") String corpId,
            @Param("scopeVersion") Long scopeVersion,
            @Param("limit") Long limit
    );

    /**
     * 根据corpId查询部门列表
     * @param parentId
     * @return
     */
    List<CorpDepartmentDO> getCorpDepartmentListByParentIdAndScopeVersion(
            @Param("corpId") String corpId,
            @Param("parentId") Long parentId,
            @Param("scopeVersion") Long scopeVersion
    );
}
