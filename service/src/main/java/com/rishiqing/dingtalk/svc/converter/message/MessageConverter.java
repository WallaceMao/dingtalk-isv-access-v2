package com.rishiqing.dingtalk.svc.converter.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.enumtype.MessageType;
import com.rishiqing.dingtalk.api.model.vo.message.MessageBody;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;
import com.rishiqing.dingtalk.api.model.vo.suite.AppVO;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:49
 */
public class MessageConverter {
    /**
     * 普通消息通知的转换，将json格式转换成MessageVO格式
     * @param json
     * @return
     */
    public static MessageVO json2MessageVO(String corpId, Long agentId, JSONObject json){
        if(json == null){
            return null;
        }
        String msgType = json.getString("msgtype");
        MessageType type = MessageType.getMessageType(msgType);
        //  如果msgType解析不了，返回空
        if(type == null){
            return null;
        }

        MessageVO message = new MessageVO();
        message.setCorpId(corpId);
        message.setAgentId(agentId);
        message.setMsgType(type);

        if(json.containsKey("userid_list")){
            String[] userArray  = json.getString("userid_list").split(",");
            List<String> userIdList = Arrays.asList(userArray);
            message.setUserIdList(userIdList);
        }
        if(json.containsKey("dept_id_list")){
            List<Long> deptIdList = new ArrayList<>();
            String[] strDeptIdArray = json.getString("dept_id_list").split(",");
            for (int i = 0; i < strDeptIdArray.length; i++){
                deptIdList.add(Long.valueOf(strDeptIdArray[i]));
            }
            message.setDeptIdList(deptIdList);
        }
        JSONObject msgContent = json.getJSONObject("msgcontent");
        MessageBody messageBody = parseMessageBody(type, msgContent);
        if (messageBody != null) {
            messageBody.setMessageType(type);
        }

        message.setMessageBody(messageBody);

        return message;
    }

    /**
     * 日事清后台推送过来的通知，由于格式不一样，需要做单独的转换
     * @return
     */
    public static MessageVO rsqJson2MessageVO(AppVO app, String corpId, Long agentId, String msgType, JSONObject jsonOrg){
        if(jsonOrg == null){
            return null;
        }
        MessageType messageType = MessageType.getMessageType(msgType);
        //  如果msgType解析不了，返回空
        if(messageType == null){
            return null;
        }
        MessageVO message = new MessageVO();
        message.setCorpId(corpId);
        message.setAgentId(agentId);
        message.setMsgType(messageType);

        if(jsonOrg.containsKey("touser")){
            String[] userArray  = jsonOrg.getString("touser").split("\\|");
            List<String> userIdList = Arrays.asList(userArray);
            message.setUserIdList(userIdList);
        }
        JSONObject json = jsonOrg.getJSONObject("textcard");

        MessageBody.OABody oaBody = new MessageBody.OABody();
        oaBody.setMessageType(messageType);
        String url = json.getString("url");
        String title = json.getString("title");
        String desc = json.getString("description");
        String btntxt = json.getString("btntxt");
        String from, type, receiverName;

        //  对url做修正处理，暂时这样实现
        String newUrl = url.replace("backauth", "dingtalk/workbei");
        oaBody.setMessageUrl(newUrl);
        //  pc端消息链接
        if (json.containsKey("pc_message_url")) {
            String pcMessageUrl = json.getString("pc_message_url");
            String newPcMessageUrl = pcMessageUrl.replace("backauth", "dingtalk/workbei-pc");
            oaBody.setPcMessageUrl(newPcMessageUrl);
        }

        MessageBody.OABody.Head head = new MessageBody.OABody.Head();
        head.setText(app.getAppName());
        head.setBgColor(app.getMainColor());
        oaBody.setHead(head);

        MessageBody.OABody.Body body = new MessageBody.OABody.Body();
        body.setTitle(Jsoup.parse(desc).body().text());

        List<MessageBody.OABody.Body.Form> formList = new ArrayList<MessageBody.OABody.Body.Form>();

        MessageBody.OABody.Body.Form titleItem = new MessageBody.OABody.Body.Form();
        titleItem.setKey("任务: ");
        titleItem.setValue(title);
        formList.add(titleItem);

        if(json.containsKey("from")){
            from = json.getString("from");
        }
        if(json.containsKey("type")){
            type = json.getString("type");
        }
        if(json.containsKey("receiverName")){
            receiverName = json.getString("receiverName");
            if(receiverName != null){
                MessageBody.OABody.Body.Form receiverItem = new MessageBody.OABody.Body.Form();
                receiverItem.setKey("成员: ");
                receiverItem.setValue(receiverName);
                formList.add(receiverItem);
            }
        }
        if(json.containsKey("todoDate")){
            String todoDate = json.getString("todoDate");
            if(todoDate != null){
                MessageBody.OABody.Body.Form todoDateItem = new MessageBody.OABody.Body.Form();
                //  日期格式为yyyy.mm.dd的形式
                int size = Math.min(todoDate.length(), 10);
                todoDateItem.setKey("日期: ");
                todoDateItem.setValue(todoDate.substring(0, size));
                formList.add(todoDateItem);
            }
        }
        MessageBody.OABody.Body.Form btnItem = new MessageBody.OABody.Body.Form();
        btnItem.setKey("点击查看>>");
        btnItem.setValue("");
        formList.add(btnItem);

        //  desc可能为富文本，从中提取出文字
//        body.setContent(Jsoup.parse(desc).body().text());
        body.setForm(formList);

        oaBody.setBody(body);

        message.setMessageBody(oaBody);

        return message;
    }

    private static MessageBody parseMessageBody(MessageType type, JSONObject json){
        switch (type){
            case TEXT:
                return parseTextBody(json);
            case IMAGE:
                return parseImageBody(json);
            case VOICE:
                return parseVoiceBody(json);
            case FILE:
                return parseFileBody(json);
            case LINK:
                return parseLinkBody(json);
            case OA:
                return parseOABody(json);
            case MARKDOWN:
                return parseMarkdownBody(json);
            case ACTION_CARD:
                return parseActionCardBody(json);
            default:
                throw new IllegalArgumentException("parseMessageBody type invalid, " + type + ", json: " + json);
        }
    }

    private static MessageBody parseTextBody(JSONObject json){
        MessageBody.TextBody body = new MessageBody.TextBody();
        body.setContent(json.getString("content"));
        return body;
    }
    private static MessageBody parseImageBody(JSONObject json){
        MessageBody.ImageBody body = new MessageBody.ImageBody();
        body.setMediaId(json.getString("media_id"));
        return body;
    }
    private static MessageBody parseVoiceBody(JSONObject json){
        MessageBody.VoiceBody body = new MessageBody.VoiceBody();
        body.setMediaId(json.getString("media_id"));
        body.setDuration(json.getString("duration"));
        return body;
    }
    private static MessageBody parseFileBody(JSONObject json){
        MessageBody.FileBody body = new MessageBody.FileBody();
        body.setMediaId(json.getString("media_id"));
        return body;
    }
    private static MessageBody parseLinkBody(JSONObject json){
        MessageBody.LinkBody body = new MessageBody.LinkBody();
        body.setMessageUrl(json.getString("messageUrl"));
        body.setPicUrl(json.getString("picUrl"));
        body.setText(json.getString("text"));
        body.setTitle(json.getString("title"));
        return body;
    }
    private static MessageBody parseOABody(JSONObject json){
        MessageBody.OABody oaBody = new MessageBody.OABody();

        oaBody.setMessageUrl(json.getString("message_url"));
        if(json.containsKey("pc_message_url")){
            oaBody.setPcMessageUrl(json.getString("pc_message_url"));
        }

        if(json.containsKey("head")){
            MessageBody.OABody.Head head = new MessageBody.OABody.Head();
            JSONObject jsonHead = json.getJSONObject("head");
            //  head.text最多10个字符
            String title = jsonHead.getString("text");
            if(title.length() > 10){
                title = title.substring(0, 10);
            }
            head.setText(title);
            head.setBgColor(jsonHead.getString("bgcolor"));
            oaBody.setHead(head);
        }

        if(json.containsKey("body")){
            MessageBody.OABody.Body body = new MessageBody.OABody.Body();
            JSONObject jsonBody = json.getJSONObject("body");

            if(jsonBody.containsKey("title")){
                body.setTitle(jsonBody.getString("title"));
            }

            if(jsonBody.containsKey("form")){
                JSONArray formArray = jsonBody.getJSONArray("form");
                List<MessageBody.OABody.Body.Form> list = new ArrayList<>(formArray.size());
                for(int i = 0; i < formArray.size(); i++){
                    JSONObject jsonForm = formArray.getJSONObject(i);
                    MessageBody.OABody.Body.Form form = new MessageBody.OABody.Body.Form();
                    form.setKey(jsonForm.getString("key"));
                    form.setValue(jsonForm.getString("value"));
                    list.add(form);
                }
                body.setForm(list);
            }

            if(jsonBody.containsKey("rich")){
                MessageBody.OABody.Body.Rich rich = new MessageBody.OABody.Body.Rich();
                JSONObject object = jsonBody.getJSONObject("rich");
                rich.setNum(object.getString("num"));
                rich.setUnit(object.getString("unit"));
                body.setRich(rich);
            }
            if(jsonBody.containsKey("content")){
                body.setContent(jsonBody.getString("content"));
            }
            if(jsonBody.containsKey("image")){
                body.setImage(jsonBody.getString("image"));
            }

            if(jsonBody.containsKey("file_count")){
                body.setFileCount(jsonBody.getString("file_count"));
            }
            if(jsonBody.containsKey("author")){
                body.setAuthor(jsonBody.getString("author"));
            }
            oaBody.setBody(body);
        }

        return oaBody;
    }
    private static MessageBody parseMarkdownBody(JSONObject json){
        MessageBody.MarkdownBody body = new MessageBody.MarkdownBody();
        body.setTitle(json.getString("title"));
        body.setText(json.getString("text"));
        return body;
    }
    private static MessageBody parseActionCardBody(JSONObject json){
        MessageBody.ActionCardBody body = new MessageBody.ActionCardBody();
        body.setTitle(json.getString("title"));
        body.setMarkdown(json.getString("markdown"));
        if(json.containsKey("single_title")){
            body.setSingleTitle(json.getString("single_title"));
        }
        if(json.containsKey("single_url")){
            body.setSingleUrl(json.getString("single_url"));
        }
        if(json.containsKey("btn_orientation")){
            body.setBtnOrientation(json.getString("btn_orientation"));
        }
        if(json.containsKey("btn_json_list")){
            List<MessageBody.ActionCardBody.Button> btnList = new ArrayList<>();
            JSONArray btnArray = json.getJSONArray("btn_json_list");
            for(int i = 0; i < btnArray.size(); i++){
                JSONObject jsonBtn = btnArray.getJSONObject(i);
                MessageBody.ActionCardBody.Button btn = new MessageBody.ActionCardBody.Button();
                btn.setTitle(jsonBtn.getString("title"));
                btn.setActionUrl(jsonBtn.getString("action_url"));
                btnList.add(btn);
            }
            body.setBtnJsonList(btnList);
        }
        return body;
    }
}
