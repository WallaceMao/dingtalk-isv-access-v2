package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.dingtalk.ManagerDaoDingmainBaseUnitTest;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.mgr.dingmain.constant.ManagerDaoDingmainConstant;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.util.TestCorpFactory;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.util.TestDepartmentFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional(transactionManager = "transactionManager")
@Rollback
public class CorpDepartmentManagerDaoDingmainImplTest extends ManagerDaoDingmainBaseUnitTest {
    private CorpVO corpVO;

    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpDepartmentManager corpDepartmentManager;

    @Before
    public void setUp() throws Exception {
        CorpVO corpVO = TestCorpFactory.getCorpVO();
        corpManager.saveOrUpdateCorp(corpVO);

        this.corpVO = corpVO;
    }

    /**
     * 测试如果公司没有任何部门
     */
    @Test
    public void testListTopCorpDepartmentByScopeVersionWithoutDepartment(){
        String corpId = this.corpVO.getCorpId();
        Long scopeVersion = this.corpVO.getScopeVersion();
        List<CorpDepartmentVO> corpList = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, scopeVersion);

        assertThat(corpList).isNull();
    }

    /**
     * 测试公司有根部门，那么返回根部门
     */
    @Test
    public void testListTopCorpDepartmentByScopeVersionWithoutRootDepartment() throws Exception{
        String corpId = this.corpVO.getCorpId();
        Long scopeVersion = this.corpVO.getScopeVersion();

        CorpDepartmentVO rootDeptVO = TestDepartmentFactory.getRootCorpDepartmentVO(corpId, scopeVersion);
        corpDepartmentManager.saveOrUpdateCorpDepartment(rootDeptVO);
        assertThat(rootDeptVO.getDeptId()).isEqualTo(ManagerDaoDingmainConstant.DEPT_ID_ROOT);
        assertThat(rootDeptVO.getParentId()).isNull();

        CorpDepartmentVO childDeptVO = TestDepartmentFactory.getCorpDepartmentVO(corpId, rootDeptVO.getDeptId(), scopeVersion);
        corpDepartmentManager.saveOrUpdateCorpDepartment(childDeptVO);
        assertThat(childDeptVO.getParentId()).isEqualTo(rootDeptVO.getDeptId());

        List<CorpDepartmentVO> corpList = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, scopeVersion);
        assertThat(corpList.size()).isEqualTo(1);
        assertThat(corpList).extracting("corpId", "deptId", "scopeVersion")
                .contains(tuple(rootDeptVO.getCorpId(), rootDeptVO.getDeptId(), rootDeptVO.getScopeVersion()));
    }

    /**
     * 测试公司如果没有根部门，那么返回顶级的子部门列表。
     * 例如：公司有如下部门：
     * ROOT - A1 - A2
     *      - B1 - B2
     *      - C1 - C2 - C3
     * 那么如果可见范围为A1、A2、B2、C2、C3，那么listTopCorpDepartmentByScopeVersion返回的应该返回包含A1、B2、C2的列表
     */
    @Test
    public void testListTopCorpDepartmentByScopeVersionWithoutSubDepartment() throws Exception{
        String corpId = this.corpVO.getCorpId();
        Long scopeVersion = this.corpVO.getScopeVersion();
        CorpDepartmentVO rootDeptVO = TestDepartmentFactory.getRootCorpDepartmentVO(corpId, scopeVersion);
        Long rootDeptId = rootDeptVO.getDeptId();
        CorpDepartmentVO deptA1 = TestDepartmentFactory.getCorpDepartmentVO(corpId, rootDeptId, scopeVersion);
        CorpDepartmentVO deptA2 = TestDepartmentFactory.getCorpDepartmentVO(corpId, deptA1.getDeptId(), scopeVersion);
        CorpDepartmentVO deptB1 = TestDepartmentFactory.getCorpDepartmentVO(corpId, rootDeptId, scopeVersion);
        CorpDepartmentVO deptB2 = TestDepartmentFactory.getCorpDepartmentVO(corpId, deptB1.getDeptId(), scopeVersion);
        CorpDepartmentVO deptC1 = TestDepartmentFactory.getCorpDepartmentVO(corpId, rootDeptId, scopeVersion);
        CorpDepartmentVO deptC2 = TestDepartmentFactory.getCorpDepartmentVO(corpId, deptC1.getDeptId(), scopeVersion);
        CorpDepartmentVO deptC3 = TestDepartmentFactory.getCorpDepartmentVO(corpId, deptC2.getDeptId(), scopeVersion);

        corpDepartmentManager.saveOrUpdateCorpDepartment(deptA1);
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptA2);
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptB2);
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptC2);
        corpDepartmentManager.saveOrUpdateCorpDepartment(deptC3);
        List<CorpDepartmentVO> corpList = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, scopeVersion);
        assertThat(corpList.size()).isEqualTo(3);
        assertThat(corpList).extracting("corpId", "deptId", "scopeVersion")
                .contains(tuple(deptA1.getCorpId(), deptA1.getDeptId(), deptA1.getScopeVersion()),
                        tuple(deptB2.getCorpId(), deptB2.getDeptId(), deptB2.getScopeVersion()),
                        tuple(deptC2.getCorpId(), deptC2.getDeptId(), deptC2.getScopeVersion()));
    }
}