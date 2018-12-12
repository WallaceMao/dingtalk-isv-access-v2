# job定时任务

## 配置文件
`${user.home}\dingtalk\alijob-config.properties`
```
# 阿里云的定时任务，必须这样写
accessKey=xxxxxx
secretKey=yyyyyy
alijob.groupId=123-1-2-1234
alijob.regionName=cn-test
```
## JVM参数
```
-Dspas.identity=C:\Users\czip\dingtalk\alijob-config.properties
```