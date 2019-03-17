package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgRemoveSyncActionHandler implements SyncActionHandler {
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ：为企业corpid
     biz_data         ：数据为如下两种 Json格式
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        //  删除团队后，暂时不会做任何处理
    }
}
