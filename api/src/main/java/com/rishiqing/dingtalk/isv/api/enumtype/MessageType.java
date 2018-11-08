package com.rishiqing.dingtalk.isv.api.enumtype;

/**
 * 发送的消息格式
 * @link https://open-doc.dingtalk.com/microapp/serverapi2/pgoxpy
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public enum MessageType {

    TEXT("text"),
    IMAGE("image"),
    VOICE("voice"),
    FILE("file"),
    LINK("link"),
    OA("oa"),
    MARKDOWN("markdown"),
    ACTION_CARD("action_card");

    private final String key;

    MessageType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static MessageType getMessageType(String key){
        MessageType[] typeArray = MessageType.values();
        for (MessageType o : typeArray) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}