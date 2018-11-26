package com.rishiqing.dingtalk.taskjob;

import com.alibaba.edas.schedulerx.SchedulerXClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wallace Mao
 * Date: 2018-11-26 0:32
 */
public class ClientMain {
    private static final Logger logger = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
//        SchedulerXClient schedulerXClient = new SchedulerXClient();
//        /*
//         * get basic environment variables
//         */
//        //获取该客户端的groupId
//        String groupId = System.getenv("SCX_GROUP_ID");
//        //获取该客户端运行的region，EDAS概念
//        String regionName = System.getenv("SCX_REGION");
//        //获取该客户端要访问的Console damain （注：SchedulerX使用Console做server的服务发现）
//        String domainName = System.getenv("SCX_DOMAIN");
//        if (groupId == null  || groupId.trim().isEmpty()) {
//            throw new IllegalArgumentException("please make sure 'SCX_GROUP_ID' env variable not empty.");
//        }
//        if (regionName == null || regionName.trim().isEmpty()) {
//            throw new IllegalArgumentException("please make sure 'SCX_REGION' env variable not empty.");
//        }
//        schedulerXClient.setGroupId(groupId);
//        schedulerXClient.setRegionName(regionName);
//        schedulerXClient.setNewVersion(true);
//        if (domainName != null && !domainName.trim().isEmpty()) {
//            schedulerXClient.setDomainName(domainName);
//        }
        //正式启动客户端
        try {
//            schedulerXClient.init();
            System.out.println("hello=====");
        } catch (Exception e) {
            logger.error("error in ClientMain: ", e);
        }
    }
}
