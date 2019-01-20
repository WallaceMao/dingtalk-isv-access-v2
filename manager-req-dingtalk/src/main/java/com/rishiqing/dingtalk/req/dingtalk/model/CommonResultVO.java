package com.rishiqing.dingtalk.req.dingtalk.model;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 15:00
 */
public class CommonResultVO {
    private Long errcode;
    private String errmsg;

    public static CommonResultVO buildCommonResult(Long errcode, String errmsg){
        CommonResultVO commonResultVO = new CommonResultVO();
        commonResultVO.setErrcode(errcode);
        commonResultVO.setErrmsg(errmsg);
        return commonResultVO;
    }

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "CommonResultVO{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
