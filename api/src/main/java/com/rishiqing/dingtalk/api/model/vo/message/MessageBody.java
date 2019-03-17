package com.rishiqing.dingtalk.api.model.vo.message;

import com.rishiqing.dingtalk.api.enumtype.MessageType;

import java.io.Serializable;
import java.util.List;

/**
 * 消息类型参照钉钉文档
 * @link https://open-doc.dingtalk.com/microapp/serverapi2/ye8tup
 * @author Wallace Mao
 * Date: 2018-11-08 15:07
 */
public class MessageBody implements Serializable {
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public static class TextBody extends MessageBody {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ImageBody extends MessageBody {
        private String mediaId;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    public static class VoiceBody extends MessageBody {
        private String mediaId;
        private String duration;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    public static class FileBody extends MessageBody {
        private String mediaId;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    public static class LinkBody extends MessageBody {
        private String messageUrl;
        private String picUrl;
        private String title;
        private String text;

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class OABody extends MessageBody {
        private String messageUrl;
        private String pcMessageUrl;
        private Head head;
        private Body body;

        public static class Head implements Serializable {
            private String bgColor;
            private String text;

            public String getBgColor() {
                return bgColor;
            }

            public void setBgColor(String bgColor) {
                this.bgColor = bgColor;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class Body implements Serializable {
            private String title;
            private String content;
            private String image;
            private String fileCount;
            private String author;
            private List<Form> form;
            private Rich rich;

            public static class Form implements Serializable {
                private String key;
                private String value;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class Rich implements Serializable {
                private String num;
                private String unit;

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getFileCount() {
                return fileCount;
            }

            public void setFileCount(String fileCount) {
                this.fileCount = fileCount;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<Form> getForm() {
                return form;
            }

            public void setForm(List<Form> form) {
                this.form = form;
            }

            public Rich getRich() {
                return rich;
            }

            public void setRich(Rich rich) {
                this.rich = rich;
            }
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        public String getPcMessageUrl() {
            return pcMessageUrl;
        }

        public void setPcMessageUrl(String pcMessageUrl) {
            this.pcMessageUrl = pcMessageUrl;
        }

        public Head getHead() {
            return head;
        }

        public void setHead(Head head) {
            this.head = head;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    public static class MarkdownBody extends MessageBody {
        private String title;
        private String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class ActionCardBody extends MessageBody {
        private String title;
        private String markdown;

        private String singleTitle;
        private String singleUrl;

        private String btnOrientation;
        private List<Button> btnJsonList;

        public static class Button implements Serializable {
            private String title;
            private String actionUrl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getActionUrl() {
                return actionUrl;
            }

            public void setActionUrl(String actionUrl) {
                this.actionUrl = actionUrl;
            }
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMarkdown() {
            return markdown;
        }

        public void setMarkdown(String markdown) {
            this.markdown = markdown;
        }

        public String getSingleTitle() {
            return singleTitle;
        }

        public void setSingleTitle(String singleTitle) {
            this.singleTitle = singleTitle;
        }

        public String getSingleUrl() {
            return singleUrl;
        }

        public void setSingleUrl(String singleUrl) {
            this.singleUrl = singleUrl;
        }

        public String getBtnOrientation() {
            return btnOrientation;
        }

        public void setBtnOrientation(String btnOrientation) {
            this.btnOrientation = btnOrientation;
        }

        public List<Button> getBtnJsonList() {
            return btnJsonList;
        }

        public void setBtnJsonList(List<Button> btnJsonList) {
            this.btnJsonList = btnJsonList;
        }
    }
}
