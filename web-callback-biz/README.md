# 业务回调事件的接收端

实现功能：

1. 管理回调接口。参考[钉钉管理回调](https://open-doc.dingtalk.com/microapp/serverapi2/pwz3r5)
2. 将接收到的event插入到事件队列中

## ngrok使用
windows: `ngrok -subdomain=dev6 -config=ngrok.cfg 8082`