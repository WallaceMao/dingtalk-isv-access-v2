package com.rishiqing.dingtalk.biz.exception;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 18:19
 */
public class HttpRequestException extends RuntimeException {
    private long errcode;
    private String errmsg;

    public HttpRequestException() {
        super();
    }

    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }

    protected HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpRequestException(long errcode, String errmsg){
        super(buildMessage(errcode, errmsg));
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private static String buildMessage(long errcode, String errmsg) {
        return "http error, errcode is " + errcode + ", errmsg is " + errmsg;
    }
}
