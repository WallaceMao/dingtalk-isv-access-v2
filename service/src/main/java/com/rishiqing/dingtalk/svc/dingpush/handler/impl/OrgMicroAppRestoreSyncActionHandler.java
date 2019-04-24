package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgMicroAppRestoreSyncActionHandler implements SyncActionHandler {
    /**
     * 功能：人数限制
     * 场景：用户授权开通微应用的时候
     * 逻辑：调用获取公司人数的接口，如果超过人数限制，不再进行同步，标记这个公司的人数过大，标记钉钉云push（open_sync_biz_data）的状态为-2；
     * 在isv_corp_sync_filter (id,  gmt_create, gmt_modified, corp_id, count, status )
     * <p>
     * subscribe_id  ： 套件suiteid加下划线0
     * copp_id : 开通套件微应用的企业corpid
     * biz_id             ： 微应用的appid
     * biz_data         ：数据为如下3种Json格式
     *
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        //  暂时不会做任何处理
        //调用获取公司人数的接口
        //如果超过人数限制，不再进行同步
        //标记这个公司的人数过大，标记钉钉云push（open_sync_biz_data）的状态为-2
    }
}
