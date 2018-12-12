# 功倍

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
mvn install:install-file -Dfile=.\service\lib\taobao-sdk-java-auto_1479188381469-20181101.jar -DgroupId=com.dingtalk.open -DartifactId=taobao-sdk-java -Dversion=1.0 -Dpackaging=jar
```

3. 升级jdk11的问题
taobao-sdk-java.jar包不能直接兼容jdk11，需要找到源码重新编译。
```
mvn clean test-compile -pl taobao-sdk-java -am
mvn clean test-compile -pl taobao-sdk-java -am -X
```