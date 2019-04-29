package com.rishiqing.dingtalk.api.exception;

/**
 * @author: Da Shuai
 * @create: 2019-04-29 11:22
 */
public class CorpOverstaffedException extends RuntimeException {
    public CorpOverstaffedException() {
    }

    public CorpOverstaffedException(String message) {
        super(message);
    }

    public CorpOverstaffedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorpOverstaffedException(Throwable cause) {
        super(cause);
    }

    public CorpOverstaffedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
