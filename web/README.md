# 钉钉云推送的事件：

## 部门相关：
deptId：92589595
在可见范围之外创建部门：
{"errcode":50004,"syncAction":"org_dept_create","errmsg":"请求的部门id不在授权范围内"}

可见范围之内创建部门：
deptId：92616561
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_create","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"前端组","id":92616561,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92616561}

可见范围之外删除部门：
deptId：87600834
{"syncAction":"org_dept_remove"}
如果部门有成员：在钉钉中不允许删除
如果部门有子部门，那么会级联删除

可见范围之内删除部门：
deptId：92809032
{"syncAction":"org_dept_remove"}
如果部门有成员：在钉钉中不允许删除
如果部门有子部门，那么会级联删除

在可见范围外移动部门：
deptId：92469982
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
如果含有子部门：测试无事件推送

将可见范围内移动部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"后台组","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}
如果含有子部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","orgDeptOwner":"","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":92616561,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"deptGroupChatId":"chatf4fc8ed6461dce2881c5d992932be535","name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}

将可见范围内移动到可见范围外：
deptId：92469982
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
部门的所有下级部门以及下级部门的人员都会被移动：
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

将可见范围外移动到可见范围内：
deptId：92469982
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"销售一部","id":92469982,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92469982}
如果含有子部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","orgDeptOwner":"","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"deptGroupChatId":"chatf4fc8ed6461dce2881c5d992932be535","name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}

可见范围外修改部门名称：
无数据

可见范围内修改部门名称：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}

## 成员接口：

可见范围之外新增成员：
{"errcode":50002,"syncAction":"user_add_org","errmsg":"请求的员工userid不在授权范围内"}

可见范围之内新增成员：
{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_add_org","userid":"0205536526115909","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373034540521512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"name":"曹德季","position":""}

可见范围之外删除员工：
{"syncAction":"user_leave_org"}

可见范围之内删除员工：
{"syncAction":"user_leave_org"}

可见范围外移动员工部门：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围内移动员工部门：
{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_role_change","userid":"0205536526115909","isLeaderInDepts":"{92616561:false}","isBoss":false,"isSenior":false,"department":[92616561],"orderInDepts":"{92616561:176373034032769512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"曹德季","position":""}

可见范围外移动到可见范围内：
{"errcode":0,"unionid":"GyNPc4RzoNsiE","syncAction":"user_role_change","userid":"01340365-896931084","isLeaderInDepts":"{1:false,87007170:false}","isBoss":false,"isSenior":false,"department":[1,87007170],"orderInDepts":"{1:176373034157534512,87007170:176373033984031512}","dingId":"$:LWCP_v1:$m0E4r38Z2GofUxkWrvW80g==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPACOG82GaiKDNBNfNBNc_1239_1239.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"日事清创始人-刘磊","position":""}

可见范围内移动到可见范围外：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围外修改成员名称：
{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}

可见范围内修改成员名称：
{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_modify_org","userid":"0205536526115909","isLeaderInDepts":"{92616561:false}","isBoss":false,"isSenior":false,"department":[92616561],"orderInDepts":"{92616561:176373034032769512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"曹德季（测试）","position":""}
当用户出现这种推送事件时，可能用户是不存在的！
{"errcode":0,"unionid":"l5HP6ZjTOjii2CsIRbXXTUAiEiE","syncAction":"user_modify_org","userid":"15450072077226545","isLeaderInDepts":"{67057527:false}","isBoss":false,"isSenior":false,"hiredDate":1544976000000,"department":[67057527],"orderInDepts":"{67057527:176372614058705512}","dingId":"$:LWCP_v1:$5KoaYrqdTVa5HFoUvSfEaCvGdj7XxWO2","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"isHide":false,"name":"杨柳青"}

## 公司信息
修改公司信息：
{"errcode":0,"corpid":"dingb3a4951a0f510b2f35c2f4657eb6378f","auth_level":0,"syncAction":"org_update","errmsg":"ok","industry":"硬件设施服务","is_authenticated":false,"corp_name":"Google走走走","corp_logo_url":""}
同时会修改顶级部门：
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

## 角色
新增角色：
{"role_name":"测试角色","role_id":385618074,"group_id":363155532,"group_name":"岗位","syncAction":"org_role_add"}

修改角色：
{"role_name":"测试角色（测试）","role_id":385618074,"group_id":363155532,"group_name":"岗位","syncAction":"org_role_modify"}

可见范围外关联角色：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围内关联角色：
{"errcode":0,"unionid":"TM88VnKeL3wOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"160048360721049991","isLeaderInDepts":"{87007171:false,87007170:false}","isBoss":false,"isSenior":false,"department":[87007171,87007170],"orderInDepts":"{87007171:176373068359270512,87007170:176376138601535512}","dingId":"$:LWCP_v1:$cWFrcYlg7AOD3uBeMD9vIA==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1dIzHs3NBNrNBNc_1239_1242.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"刘梦婕","position":""}

可见范围内移除角色关联：
{"errcode":0,"unionid":"TM88VnKeL3wOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"160048360721049991","isLeaderInDepts":"{87007171:false,87007170:false}","isBoss":false,"isSenior":false,"department":[87007171,87007170],"orderInDepts":"{87007171:176373068359270512,87007170:176376138601535512}","dingId":"$:LWCP_v1:$cWFrcYlg7AOD3uBeMD9vIA==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1dIzHs3NBNrNBNc_1239_1242.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"刘梦婕","position":""}

可见范围外移除角色关联：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围外设置子管理员：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围内设置子管理员：
{"errcode":0,"unionid":"jGmG9yGL93YOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"10335200371148696","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373066313505512}","dingId":"$:LWCP_v1:$geyR6MC6f9A5B+m9JYXwvg==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPDgQ9qYZ5d0NJOg_58_73.jpg","isAdmin":true,"isHide":false,"jobnumber":"","name":"赵旭","position":""}

可见范围外移除子管理员：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

可见范围内移除子管理员：
{"errcode":0,"unionid":"jGmG9yGL93YOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"10335200371148696","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373066313505512}","dingId":"$:LWCP_v1:$geyR6MC6f9A5B+m9JYXwvg==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPDgQ9qYZ5d0NJOg_58_73.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"赵旭","position":""}

## 当做移除可见范围操作时，如果操作对象有比较复杂的关联
可见范围中含有父子部门，将父部门移除可见范围时
？？？猜测{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

可见范围中含有部门和部门成员，将部门移除可见范围时
？？？
