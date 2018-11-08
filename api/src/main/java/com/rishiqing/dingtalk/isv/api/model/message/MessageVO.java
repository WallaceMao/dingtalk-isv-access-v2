package com.rishiqing.dingtalk.isv.api.model.message;

import com.rishiqing.dingtalk.isv.api.enumtype.MessageType;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:42
 */
public class MessageVO {
    private Long id;
    //  消息类型
    private MessageType msgType;
    //  消息发送到的公司
    private String corpId;
    //  消息发送方的应用的agentId，注意，这里是agentId，不是appId
    private Long agentId;
    //  消息的接收人的列表，钉钉中最大列表长度：20
    private List<String> userIdList;
    //  消息的接收部门的id列表，最大列表长度：20，接收者是部门id下包括子部门下的所有用户
    private List<Long> deptIdList;
    //  消息体
    private MessageBody messageBody;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", msgType='" + msgType + '\'' +
                ", corpId='" + corpId + '\'' +
                ", agentId=" + agentId +
                ", userIdList=" + userIdList +
                ", deptIdList=" + deptIdList +
                ", messageBody=" + messageBody +
                '}';
    }
}
