package com.memory.mina.entity;

import java.io.Serializable;
import java.util.Date;

public class CloseMessage implements Serializable {
    private static final long serialVersionUID = 7613028390325773474L;
    private String uid;
    private Date date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CloseMessage{" +
                "uid='" + uid + '\'' +
                ", date=" + date +
                '}';
    }
}
