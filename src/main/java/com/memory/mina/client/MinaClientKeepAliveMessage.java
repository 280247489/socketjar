package com.memory.mina.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class MinaClientKeepAliveMessage implements KeepAliveMessageFactory {
    /** 心跳包内容 */
    private static final String HEARTBEATREQUEST = "0x01";
    private static final String HEARTBEATRESPONSE = "0x02";

    public boolean isRequest(IoSession ioSession, Object o) {
        if (o.equals(HEARTBEATREQUEST))
            return true;
        return false;
    }

    public boolean isResponse(IoSession ioSession, Object o) {
        if(o.equals(HEARTBEATRESPONSE))
            return true;
        return false;
    }

    public Object getRequest(IoSession ioSession) {
        System.out.println("heartbeat");
        return HEARTBEATREQUEST;
    }

    public Object getResponse(IoSession ioSession, Object o) {
        return null;
    }
}
