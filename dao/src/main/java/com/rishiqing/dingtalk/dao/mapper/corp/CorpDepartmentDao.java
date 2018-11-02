package com.rishiqing.dingtalk.dao.mapper.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDepartmentDO;
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
    public void saveOrUpdateCorpDepartment(CorpDepartmentDO corpDepartmentDO);

    /**
     * 更新第三方应用id信息
     * @param corpDepartmentDO
     * @return
     */
    public void updateCorpDepartmentRsqInfo(CorpDepartmentDO corpDepartmentDO);

    /**
     * 根据corpId和deptId删除部门
     * @param corpId
     * @param deptId
     * @return
     */
    public void deleteCorpDepartmentByCorpIdAndDeptId(@Param("corpId") String corpId, @Param("deptId") Long deptId);

    /**
     * 根据corpId和deptId查询部门
     * @param corpId
     * @param deptId
     * @return
     */
    public CorpDepartmentDO getCorpDepartmentByCorpIdAndDeptId(@Param("corpId") String corpId, @Param("deptId") Long deptId);

    /**
     * 根据corpId查询部门列表
     * @param corpId
     * @return
     */
    public List<CorpDepartmentDO> getCorpDepartmentListByCorpId(@Param("corpId") String corpId);

    /**
     * 根据corpId查询部门列表
     * @param parentId
     * @return
     */
    public List<CorpDepartmentDO> getCorpDepartmentListByParentId(@Param("corpId") String corpId, @Param("parentId") Long parentId);
}
