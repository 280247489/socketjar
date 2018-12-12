package com.memory.mina.entity;

import java.io.Serializable;

public class SysMessage implements Serializable {
    private static final long serialVersionUID = -2734330007371247774L;
    private String id;
    private String sysType;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SysMessage{" +
                "id='" + id + '\'' +
                ", sysType='" + sysType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
