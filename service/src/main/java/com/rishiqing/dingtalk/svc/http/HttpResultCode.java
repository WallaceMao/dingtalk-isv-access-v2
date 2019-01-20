package com.rishiqing.dingtalk.svc.http;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:33
 */
public enum HttpResultCode {
    SUCCESS("0","success"),
    SYS_ERROR("-1","系统繁忙");

    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private HttpResultCode(String errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
