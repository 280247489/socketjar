package com.memory.mina.entity;

import java.io.Serializable;
import java.util.Date;

public class IMMessage implements Serializable {
    private static final long serialVersionUID = 1672637166632693038L;
    private String id;          //消息ID
    private int msgType;           //1文本，2图片，3语音，4视频，5系统
    //1txt-text, 2pic-picture, 3voi-voice, 4vid-video, 5sys-system
    private String content;     //消息内容
    private Date date;          //消息发送时间
    private String fromId;        //发送人
    private String toType;     //接收类型 group群，singel单聊
    private String toId;          //接收人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    @Override
    public String toString() {
        return "IMMessage{" +
                "id='" + id + '\'' +
                ", msgType=" + msgType +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", fromId='" + fromId + '\'' +
                ", toType='" + toType + '\'' +
                ", toId='" + toId + '\'' +
                '}';
    }
}
