# 对业务回调事件做转换，转换成与钉钉云保持一致的格式

## 部门相关：

### 在可见范围之外创建部门：
deptId：92589595
- 【钉钉云推送】：{"errcode":50004,"syncAction":"org_dept_create","errmsg":"请求的部门id不在授权范围内"}
- 【回调】：{"TimeStamp":"1547555546471","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393188],"EventType":"org_dept_create"}
- 【转换结果】：{"errcode":50004,"syncAction":"org_dept_create","errmsg":"请求的部门id不在授权范围内"}

### 可见范围之内创建部门：
deptId：92616561
【钉钉云推送】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_create","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"前端组","id":92616561,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92616561}
【回调】：{"TimeStamp":"1547555629822","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393189],"EventType":"org_dept_create"}
【转换结果】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_create","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":95768104,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":false,"name":"销售测试3组","id":97869346,"autoAddUser":false,"deptHiding":false,"deptPermits":"","order":97869346}

### 可见范围之外删除部门：
deptId：87600834
如果部门有成员：在钉钉中不允许删除
如果部门有子部门，那么会级联删除

- 【钉钉云推送】：{"syncAction":"org_dept_remove"}
- 【回调】：{"TimeStamp":"1547555719159","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393188],"EventType":"org_dept_remove"}
- 【转换结果】：{"syncAction":"org_dept_remove"}

### 可见范围之内删除部门：
deptId：92809032
如果部门有成员：在钉钉中不允许删除
如果部门有子部门，那么会级联删除

- 【钉钉云推送】：{"syncAction":"org_dept_remove"}
- 【回调】：{"TimeStamp":"1547555842228","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393190,97393189],"EventType":"org_dept_remove"}
- 【转换结果】：{"syncAction":"org_dept_remove"}

### 部门从可见范围外移动到可见范围外：
deptId：92469982
- 【钉钉云推送】：{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
如果含有子部门：测试无事件推送
- 【回调】：{"TimeStamp":"1547556117596","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393191],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

### 部门从可见范围内移动到可见范围内：
- 【钉钉云推送】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"后台组","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}
如果含有子部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","orgDeptOwner":"","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":92616561,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"deptGroupChatId":"chatf4fc8ed6461dce2881c5d992932be535","name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}
- 【回调】：{"TimeStamp":"1547556211221","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393193],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":97393192,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"销售1c组","id":97393193,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":97393193}
包含子部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":98100001,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"销售1组","id":97393192,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":97393192}

### 将可见范围内移动到可见范围外：
deptId：92469982
- 【钉钉云推送】：{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
部门的所有下级部门以及下级部门的人员都会被移动：
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
- 【回调】：{"TimeStamp":"1547556435374","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393193],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

### 将可见范围外移动到可见范围内：
deptId：92469982
- 【钉钉云推送】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"销售一部","id":92469982,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92469982}
如果含有子部门：
{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","orgDeptOwner":"","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"deptGroupChatId":"chatf4fc8ed6461dce2881c5d992932be535","name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}
- 【回调】：{"TimeStamp":"1547556591007","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393193],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":95768104,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"市场部","id":97393191,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":97393191}

### 可见范围外修改部门名称：
- 【钉钉云推送】：无数据
- 【回调】：{"TimeStamp":"1547556672872","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[95768102],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}？？？？？

### 可见范围内修改部门名称：
- 【钉钉云推送】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":87007170,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"后台组（临时）","id":92715374,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":92715374}
- 【回调】：{"TimeStamp":"1547556629215","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[97393193],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":0,"userPermits":"","userPerimits":"","syncAction":"org_dept_modify","outerDept":false,"errmsg":"ok","deptManagerUseridList":"","parentid":97393192,"groupContainSubDept":false,"outerPermitUsers":"","outerPermitDepts":"","deptPerimits":"","createDeptGroup":true,"name":"销售1d组","id":97393193,"autoAddUser":true,"deptHiding":false,"deptPermits":"","order":97393193}

------------
## 成员接口：

### 可见范围之外新增成员：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_add_org","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：{"TimeStamp":"1547556867286","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["014627181005837"],"EventType":"user_add_org"}
- 【转换结果】：{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}？？？？？实际上回调的时候先推送一个user_add_org事件，然后推送一个user_modify_org事件，后一个事件会覆盖前一个事件

### 可见范围之内新增成员：
- 【钉钉云推送】：{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_add_org","userid":"0205536526115909","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373034540521512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"name":"曹德季","position":""}
- 【回调】：{"TimeStamp":"1547556867286","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["014627181005837"],"EventType":"user_add_org"}
- 【转换结果】：{"errcode":0,"unionid":"3HcRwuK7VfXUIy4uBkgy1QiEiE","orderInDepts":"{98100001:176369889575361512}","syncAction":"user_modify_org","dingId":"$:LWCP_v1:$ISOwl2lZvnM2C7sspD1/awEuX2GJWQyp","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"userid":"2169122037-1513149549","isHide":false,"isLeaderInDepts":"{98100001:false}","isBoss":false,"isSenior":false,"name":"文强啊啊啊啊","department":[98100001]}？？？？？实际上回调的时候先推送一个user_add_org事件，然后推送一个user_modify_org事件，后一个事件会覆盖前一个事件，需要对user_modify_org做单独的兼容处理

### 可见范围之外删除员工：
- 【钉钉云推送】：{"syncAction":"user_leave_org"}
- 【回调】：{"TimeStamp":"1547556943805","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["014627181005837"],"EventType":"user_leave_org"}
- 【转换结果】：{"syncAction":"user_leave_org"}

### 可见范围之内删除员工：
- 【钉钉云推送】：{"syncAction":"user_leave_org"}
- 【回调】：{"TimeStamp":"1547556943805","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["014627181005837"],"EventType":"user_leave_org"}
- 【转换结果】：{"syncAction":"user_leave_org"}

### 可见范围外移动员工部门：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：{"TimeStamp":"1547556983357","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"user_modify_org"}
- 【转换结果】：{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}？？？？？实际回调用的是user_modify_org，需要做兼容性修改

### 可见范围内移动员工部门：
- 【钉钉云推送】：{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_role_change","userid":"0205536526115909","isLeaderInDepts":"{92616561:false}","isBoss":false,"isSenior":false,"department":[92616561],"orderInDepts":"{92616561:176373034032769512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"曹德季","position":""}
- 【回调】：
- 【转换结果】：{"errcode":0,"unionid":"3HcRwuK7VfXUIy4uBkgy1QiEiE","orderInDepts":"{97393192:176369889281111512}","syncAction":"user_modify_org","dingId":"$:LWCP_v1:$ISOwl2lZvnM2C7sspD1/awEuX2GJWQyp","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"userid":"2169122037-1513149549","isHide":false,"isLeaderInDepts":"{97393192:false}","isBoss":false,"isSenior":false,"name":"文强aa","position":"","department":[97393192]}？？？？？实际回调用的是user_modify_org，需要做兼容性修改

### 可见范围外移动到可见范围内：
- 【钉钉云推送】：{"errcode":0,"unionid":"GyNPc4RzoNsiE","syncAction":"user_role_change","userid":"01340365-896931084","isLeaderInDepts":"{1:false,87007170:false}","isBoss":false,"isSenior":false,"department":[1,87007170],"orderInDepts":"{1:176373034157534512,87007170:176373033984031512}","dingId":"$:LWCP_v1:$m0E4r38Z2GofUxkWrvW80g==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPACOG82GaiKDNBNfNBNc_1239_1239.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"日事清创始人-刘磊","position":""}
- 【回调】：{"TimeStamp":"1547557025470","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"user_modify_org"}
- 【转换结果】：{"errcode":0,"unionid":"3HcRwuK7VfXUIy4uBkgy1QiEiE","orderInDepts":"{98100001:176369888959569512}","syncAction":"user_modify_org","dingId":"$:LWCP_v1:$ISOwl2lZvnM2C7sspD1/awEuX2GJWQyp","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"userid":"2169122037-1513149549","isHide":false,"isLeaderInDepts":"{98100001:false}","isBoss":false,"isSenior":false,"name":"文强aabb","position":"","department":[98100001]}？？？？？实际回调用的是user_modify_org，需要做兼容性修改

### 可见范围内移动到可见范围外：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：{"TimeStamp":"1547557055091","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"user_modify_org"}
- 【转换结果】：{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}？？？？？实际回调用的是user_modify_org，需要做兼容性修改

### 可见范围外修改成员名称：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：{"TimeStamp":"1547557084685","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"user_modify_org"}
- 【转换结果】：{"errcode":50002,"syncAction":"user_modify_org","errmsg":"请求的员工userid不在授权范围内"}

### 可见范围内修改成员名称：
- 【钉钉云推送】：{"errcode":0,"unionid":"43HPOKOfm7AiE","syncAction":"user_modify_org","userid":"0205536526115909","isLeaderInDepts":"{92616561:false}","isBoss":false,"isSenior":false,"department":[92616561],"orderInDepts":"{92616561:176373034032769512}","dingId":"$:LWCP_v1:$LX1knh9S1J3EGwJFEY0Dag==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1rRVgq7NASHNASU_293_289.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"曹德季（测试）","position":""}
当用户出现这种推送事件时，可能用户是不存在的！
{"errcode":0,"unionid":"l5HP6ZjTOjii2CsIRbXXTUAiEiE","syncAction":"user_modify_org","userid":"15450072077226545","isLeaderInDepts":"{67057527:false}","isBoss":false,"isSenior":false,"hiredDate":1544976000000,"department":[67057527],"orderInDepts":"{67057527:176372614058705512}","dingId":"$:LWCP_v1:$5KoaYrqdTVa5HFoUvSfEaCvGdj7XxWO2","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"isHide":false,"name":"杨柳青"}
- 【回调】：
- 【转换结果】：{"errcode":0,"unionid":"3HcRwuK7VfXUIy4uBkgy1QiEiE","orderInDepts":"{98100001:176369889575361512}","syncAction":"user_modify_org","dingId":"$:LWCP_v1:$ISOwl2lZvnM2C7sspD1/awEuX2GJWQyp","errmsg":"ok","active":true,"avatar":"","isAdmin":false,"userid":"2169122037-1513149549","isHide":false,"isLeaderInDepts":"{98100001:false}","isBoss":false,"isSenior":false,"name":"文强aa","position":"","department":[98100001]}

----------
## 公司信息
### 修改公司信息：
- 【钉钉云推送】：{"errcode":0,"corpid":"dingb3a4951a0f510b2f35c2f4657eb6378f","auth_level":0,"syncAction":"org_update","errmsg":"ok","industry":"硬件设施服务","is_authenticated":false,"corp_name":"Google走走走","corp_logo_url":""}
同时会修改顶级部门：
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}
- 【回调】：{"TimeStamp":"1547557196034","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","EventType":"org_change"}
{"TimeStamp":"1547557196364","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","DeptId":[1],"EventType":"org_dept_modify"}
- 【转换结果】：{"errcode":0,"corpid":"ding1a422bb2c4531b3935c2f4657eb6378f","auth_level":0,"syncAction":"org_update","auth_channel":"","errmsg":"ok","industry":"计算机软件","corp_name":"Facebook啊","invite_url":"https://h5.dingtalk.com/invite-page/index.html?code=f7115639a8&inviterUid=2C2B2E23984EDC40","auth_channel_type":"","invite_code":"","is_authenticated":false,"license_code":"","corp_logo_url":""}
{"errcode":50004,"syncAction":"org_dept_modify","errmsg":"请求的部门id不在授权范围内"}

--------
## 角色
### 新增角色：
- 【钉钉云推送】：{"role_name":"测试角色","role_id":385618074,"group_id":363155532,"group_name":"岗位","syncAction":"org_role_add"}
- 【回调】：无
- 【转换结果】：

### 修改角色：
- 【钉钉云推送】：{"role_name":"测试角色（测试）","role_id":385618074,"group_id":363155532,"group_name":"岗位","syncAction":"org_role_modify"}
- 【回调】：无
- 【转换结果】：

### 可见范围外关联角色：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：无
- 【转换结果】：

### 可见范围内关联角色：
- 【钉钉云推送】：{"errcode":0,"unionid":"TM88VnKeL3wOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"160048360721049991","isLeaderInDepts":"{87007171:false,87007170:false}","isBoss":false,"isSenior":false,"department":[87007171,87007170],"orderInDepts":"{87007171:176373068359270512,87007170:176376138601535512}","dingId":"$:LWCP_v1:$cWFrcYlg7AOD3uBeMD9vIA==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1dIzHs3NBNrNBNc_1239_1242.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"刘梦婕","position":""}
- 【回调】：无
- 【转换结果】：

### 可见范围内移除角色关联：
- 【钉钉云推送】：{"errcode":0,"unionid":"TM88VnKeL3wOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"160048360721049991","isLeaderInDepts":"{87007171:false,87007170:false}","isBoss":false,"isSenior":false,"department":[87007171,87007170],"orderInDepts":"{87007171:176373068359270512,87007170:176376138601535512}","dingId":"$:LWCP_v1:$cWFrcYlg7AOD3uBeMD9vIA==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1dIzHs3NBNrNBNc_1239_1242.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"刘梦婕","position":""}
- 【回调】：无
- 【转换结果】：

### 可见范围外移除角色关联：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：无
- 【转换结果】：

### 可见范围外设置子管理员：
- 【钉钉云推送】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
- 【回调】：{"TimeStamp":"1547557611150","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"org_admin_add"}
- 【转换结果】：{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}

### 可见范围内设置子管理员：
- 【钉钉云推送】：{"errcode":0,"unionid":"jGmG9yGL93YOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"10335200371148696","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373066313505512}","dingId":"$:LWCP_v1:$geyR6MC6f9A5B+m9JYXwvg==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPDgQ9qYZ5d0NJOg_58_73.jpg","isAdmin":true,"isHide":false,"jobnumber":"","name":"赵旭","position":""}
- 【回调】：{"TimeStamp":"1547557673317","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["060738464935035964"],"EventType":"org_admin_add"}
- 【转换结果】：{"errcode":0,"unionid":"1PzCZdkbsTRZ9ExvKqmtuQiEiE","orderInDepts":"{95768104:176370601712612512}","syncAction":"user_role_change","dingId":"$:LWCP_v1:$godnLhF3afo5M64NqLv/QA==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPBbCc1snXr6_NBLnNBNc_1239_1209.jpg","isAdmin":true,"userid":"060738464935035964","isHide":false,"jobnumber":"","isLeaderInDepts":"{95768104:false}","isBoss":false,"isSenior":false,"name":"许亚飞","position":"","department":[95768104]}

可见范围外移除子管理员：
{"errcode":50002,"syncAction":"user_role_change","errmsg":"请求的员工userid不在授权范围内"}
【回调】：{"TimeStamp":"1547557695630","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["06362902461212159"],"EventType":"org_admin_remove"}

可见范围内移除子管理员：
{"errcode":0,"unionid":"jGmG9yGL93YOWSIN1rumiPQiEiE","syncAction":"user_role_change","userid":"10335200371148696","isLeaderInDepts":"{87007170:false}","isBoss":false,"isSenior":false,"department":[87007170],"orderInDepts":"{87007170:176373066313505512}","dingId":"$:LWCP_v1:$geyR6MC6f9A5B+m9JYXwvg==","errmsg":"ok","active":true,"avatar":"https://static.dingtalk.com/media/lADPDgQ9qYZ5d0NJOg_58_73.jpg","isAdmin":false,"isHide":false,"jobnumber":"","name":"赵旭","position":""}
【回调】：{"TimeStamp":"1547557721104","CorpId":"ding1a422bb2c4531b3935c2f4657eb6378f","UserId":["060738464935035964"],"EventType":"org_admin_remove"}