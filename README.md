# 功倍

## 架构说明

### web层

#### 钉钉云的方式

1. web：提供对外访问的接口，也包含listener，用来监听钉钉云推送的事件。
2. webjob：提供定时刷新token的功能，由阿里云的scheduleX来触发。
3. taskjob：未实现，规划中采用阿里云的scheduleX镜像来实现job。

注：
- 钉钉云的方式，钉钉会把所有事件直接插入到ding_cloud_push数据库中
- web应用轮询ding_cloud_push数据库，处理回调事件。

#### 回调的方式

1. web-callback：提供对外访问的接口以及开通应用、变更授权、下单购买等套件级别的回调。
2. webjob-callback：提供定时刷新token的功能，目前使用quartz来实现。
3. web-callback-biz：提供业务事件回调（例如通讯录回调）的管理，实现业务事件的存储。不对业务事件做实际处理，业务回调事件的处理由web-biz-callback-listener来实现。
4. web-callback-biz-listener：轮询业务回调事件队列（在数据库中存储有对列表），并将结果进行合并，插入到模拟自建的ding_cloud_push数据库中。

注：
- 回调的方式中，回调事件分两种情况处理：套件回调事件和业务回调事件。套件回调事件包括：开通应用、变更授权、下单购买等。业务回调事件包括：公司新增部门、修改部门、新增员工、修改员工等通讯录事件等。
- 对于套件回调，由web-callback接收套件回调事件，直接进行处理。
- 对于业务回调，由web-biz-callback应用接收到回调事件，然后迅速将回调事件插入到auth数据库中的isv_corp_callback_event中。然后由web-biz-callback-listener轮询isv_corp_callback_queue，将同一个公司的多个业务回调事件合并生成biz_data，插入到ding_cloud_push数据库中

##### 启动方式

1. 启动web-callback项目，并开启ngrok，使公网可访问web-callback项目
2. 登录钉钉开放平台`https://open-dev.dingtalk.com`，推送应用的ticket到web-callback，并检查是否推送成功。
3. 启动webjob-callback项目，刷新根据ticket获取最新的suite token。
4. 如果需要调试业务回调，那么开启web-callback-biz、web-callback-biz-listener项目和web项目

#### CRM

1. webcrm：用来打钉钉电话的CRM系统

## 数据库

1. 执行`db/db.sql`创建钉钉授权主库
2. 执行`db/db-dignpush.sql`在公网中创建用来接收钉钉云推送的数据库
3. 初始化数据
```
insert into isv_suite (gmt_create, gmt_modified, suite_name, suite_key, suite_secret, encoding_aes_key, token, event_receive_url, rsq_app_name, rsq_app_token, suite_id) values (now(), now(), '功倍测试', 'suiteosjuufirhjkwdtau', 'KjIE7qK3AjTHRxAQzRByirl81FeBuCBC8UUIzIKeksAcQKQXZLYCoMTuUUvs6JKz', 'xxxx', 'xxxx', 'xxxx', 'dingtalk', 'xxxxxxxxx', '4417002');
insert into isv_app (gmt_create, gmt_modified, suite_key, app_id, active_message, app_name, main_color) values (now(), now(), 'suiteosjuufirhjkwdtau', 10562, null, '功倍测试app', 'FFFFBB00');
insert into isv_suite_ticket (gmt_create, gmt_modified, suite_key, ticket) values (now(), now(), 'suiteosjuufirhjkwdtau', 'xxxx');
insert into isv_order_spec_item (gmt_create, gmt_modified, suite_key, goods_code, item_code, item_name, inner_key, rsq_product_name) values (now(), now(), 'suiteosjuufirhjkwdtau', 'GOODS_CODE', 'ITEM_CODE_TRIAL', '试用版', 'TRIAL', 'BASE_ENTERPRISE');
insert into isv_order_spec_item (gmt_create, gmt_modified, suite_key, goods_code, item_code, item_name, inner_key, rsq_product_name) values (now(), now(), 'suiteosjuufirhjkwdtau', 'GOODS_CODE', 'ITEM_CODE_STANDARD', '试用版', 'STANDARD', 'BASE_ENTERPRISE');
insert into isv_staff_popup_config (gmt_create, gmt_modified, suite_key, popup_type, sale_qr_code_url, sale_phone_number, is_disabled) values(now(), now(), 'suiteosjuufirhjkwdtau', 'SOON_EXPIRE', 'http://rishiqing-front.oss-cn-beijing.aliyuncs.com/dingtalk/dingtalk-sale-qrcode.png', '177-1037-6397', 0);
insert into isv_staff_popup_config (gmt_create, gmt_modified, suite_key, popup_type, sale_qr_code_url, sale_phone_number, is_disabled) values(now(), now(), 'suiteosjuufirhjkwdtau', 'EXPIRE', 'http://rishiqing-front.oss-cn-beijing.aliyuncs.com/dingtalk/dingtalk-sale-qrcode.png', '177-1037-6397', 0);
insert into isv_staff_popup_config (gmt_create, gmt_modified, suite_key, popup_type, sale_qr_code_url, sale_phone_number, is_disabled) values(now(), now(), 'suiteosjuufirhjkwdtau', 'OVERLOAD', 'http://rishiqing-front.oss-cn-beijing.aliyuncs.com/dingtalk/dingtalk-sale-qrcode.png', '177-1037-6397', 0);
insert into isv_staff_popup_config (gmt_create, gmt_modified, suite_key, popup_type, sale_qr_code_url, sale_phone_number, is_disabled) values(now(), now(), 'suiteosjuufirhjkwdtau', 'OLD_UPGRADE', 'http://rishiqing-front.oss-cn-beijing.aliyuncs.com/dingtalk/dingtalk-sale-qrcode.png', '177-1037-6397', 1);
```

注意：isv_suite和isv_app需要修改成对应的值

## 钉钉云对接

在钉钉中配置接收钉钉云推送的数据库，保证接收到钉钉云推送的数据

## 应用

1. 先启动web主应用。启动后，会从钉钉云推送的数据中获取suite ticket
2. 然后启动web job应用。会定时拿suite ticket获取suite token

## 注意
1. 一个公司不要同时安装正式版和测试版，否则会发生冲突！
2. 本地安装taobao sdk
```
mvn install:install-file -Dfile=.\service\lib\taobao-sdk-java-auto_1479188381469-20181204.jar -DgroupId=com.dingtalk.open -DartifactId=taobao-sdk-java -Dversion=auto_1479188381469-20181204 -Dpackaging=jar
```

3. 配置阿里云定时任务
增加系统参数：`-Dspas.identity=C:\Users\czip\dingtalk\alijob-config.properties`

4. 配置logback日志文件
通过设置系统参数：`-Dlogback.configurationFile=logback-dev.xml`。logback会去classpath下查找logback-dev.xml文件

5. 升级jdk11的问题（暂不升级）
taobao-sdk-java.jar包不能直接兼容jdk11，需要找到源码重新编译。
```
mvn clean test-compile -pl taobao-sdk-java -am
mvn clean test-compile -pl taobao-sdk-java -am -X
```