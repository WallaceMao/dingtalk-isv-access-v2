package com.rishiqing.dingtalk.biz.service.util;

/**
 * @author Wallace Mao
 * Date: 2018-11-21 15:09
 */
public interface QueueService {
    void sendToGenerateTeamSolution(String corpId, String userId);

    void sendToGenerateStaffSolution(String corpId, String userId);
}
