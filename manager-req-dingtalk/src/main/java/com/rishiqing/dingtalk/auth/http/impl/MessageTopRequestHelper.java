package com.rishiqing.dingtalk.auth.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.rishiqing.common.base.StringUtil;
import com.rishiqing.dingtalk.auth.http.MessageRequestHelper;
import com.rishiqing.dingtalk.isv.api.enumtype.MessageType;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.message.MessageBody;
import com.rishiqing.dingtalk.isv.api.model.message.MessageResultVO;
import com.rishiqing.dingtalk.isv.api.model.message.MessageVO;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:15
 */
public class MessageTopRequestHelper implements MessageRequestHelper {
    /**
     * 发送工作通知
     */
    @Override
    public MessageResultVO sendCorpConversationAsync(String corpToken, MessageVO message){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
        req.setAgentId(message.getAgentId());
        req.setToAllUser(false);
        if(message.getDeptIdList() != null && message.getDeptIdList().size() != 0){
            List<Long> deptIdList = message.getDeptIdList();
            req.setDeptIdList(StringUtil.collectionToDelimitedString(deptIdList, ","));
        }
        if(message.getUserIdList() != null && message.getUserIdList().size() != 0){
            List<String> userIdList = message.getUserIdList();
            req.setUseridList(StringUtil.collectionToDelimitedString(userIdList, ","));
        }
        req.setMsg(convertToRequestMsg(message.getMessageBody()));

        try {
            OapiMessageCorpconversationAsyncsendV2Response resp = client.execute(req, corpToken);
            MessageResultVO result = new MessageResultVO();
            result.setTaskId(resp.getTaskId());
            return result;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertToRequestMsg(MessageBody messageBody){
        MessageType type = messageBody.getMessageType();
        switch (type){
            case TEXT:
                return this.convertText((MessageBody.TextBody)messageBody);
            case IMAGE:
                return this.convertImage((MessageBody.ImageBody)messageBody);
            case VOICE:
                return this.convertVoice((MessageBody.VoiceBody)messageBody);
            case FILE:
                return this.convertFile((MessageBody.FileBody)messageBody);
            case LINK:
                return this.convertLink((MessageBody.LinkBody)messageBody);
            case OA:
                return this.convertOA((MessageBody.OABody)messageBody);
            case MARKDOWN:
                return this.convertMarkdown((MessageBody.MarkdownBody)messageBody);
            case ACTION_CARD:
                return this.convertActionCard((MessageBody.ActionCardBody)messageBody);
            default:
                return null;
        }
    }

    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertText(MessageBody.TextBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(body.getContent());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertImage(MessageBody.ImageBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
        msg.getImage().setMediaId(body.getMediaId());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertVoice(MessageBody.VoiceBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setVoice(new OapiMessageCorpconversationAsyncsendV2Request.Voice());
        msg.getVoice().setMediaId(body.getMediaId());
        msg.getVoice().setDuration(body.getDuration());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertFile(MessageBody.FileBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
        msg.getFile().setMediaId(body.getMediaId());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertLink(MessageBody.LinkBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle(body.getTitle());
        msg.getLink().setText(body.getText());
        msg.getLink().setMessageUrl(body.getMessageUrl());
        msg.getLink().setPicUrl(body.getPicUrl());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertOA(MessageBody.OABody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setMessageUrl(body.getMessageUrl());
        msg.getOa().setPcMessageUrl(body.getPcMessageUrl());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText(body.getHead().getText());
        msg.getOa().getHead().setBgcolor(body.getHead().getBgColor());

        OapiMessageCorpconversationAsyncsendV2Request.Body oaBody = new OapiMessageCorpconversationAsyncsendV2Request.Body();

        oaBody.setAuthor(body.getBody().getAuthor());
        oaBody.setTitle(body.getBody().getTitle());
        oaBody.setContent(body.getBody().getContent());
        oaBody.setFileCount(body.getBody().getFileCount());
        oaBody.setImage(body.getBody().getImage());

        if(body.getBody().getRich() != null){
            OapiMessageCorpconversationAsyncsendV2Request.Rich oaRich = new OapiMessageCorpconversationAsyncsendV2Request.Rich();
            MessageBody.OABody.Body.Rich msgRich = body.getBody().getRich();
            oaRich.setNum(msgRich.getNum());
            oaRich.setUnit(msgRich.getUnit());
            oaBody.setRich(oaRich);
        }
        if(body.getBody().getForm() != null){
            List<MessageBody.OABody.Body.Form> msgFormList = body.getBody().getForm();
            List<OapiMessageCorpconversationAsyncsendV2Request.Form> oaFormList = new ArrayList<>(msgFormList.size());
            for(MessageBody.OABody.Body.Form msgForm : msgFormList){
                OapiMessageCorpconversationAsyncsendV2Request.Form oaForm = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                oaForm.setKey(msgForm.getKey());
                oaForm.setValue(msgForm.getValue());
                oaFormList.add(oaForm);
            }
            oaBody.setForm(oaFormList);
        }

        msg.getOa().setBody(oaBody);
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertMarkdown(MessageBody.MarkdownBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText(body.getText());
        msg.getMarkdown().setTitle(body.getTitle());
        return msg;
    }
    private OapiMessageCorpconversationAsyncsendV2Request.Msg convertActionCard(MessageBody.ActionCardBody body){
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype(body.getMessageType().getKey());
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(body.getTitle());
        msg.getActionCard().setMarkdown(body.getMarkdown());
        if(body.getSingleTitle() != null){
            msg.getActionCard().setSingleTitle(body.getSingleTitle());
            msg.getActionCard().setSingleUrl(body.getSingleUrl());
        }
        if(body.getBtnOrientation() != null){
            msg.getActionCard().setBtnOrientation(body.getBtnOrientation());
            List<MessageBody.ActionCardBody.Button> msgButtonList = body.getBtnJsonList();
            List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> oaButtonList = new ArrayList<>(msgButtonList.size());
            for(MessageBody.ActionCardBody.Button msgButton : msgButtonList){
                OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList oaButton = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
                oaButton.setTitle(msgButton.getTitle());
                oaButton.setActionUrl(msgButton.getActionUrl());
                oaButtonList.add(oaButton);
            }
            msg.getActionCard().setBtnJsonList(oaButtonList);
        }

        return msg;
    }
}
