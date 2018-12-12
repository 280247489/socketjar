package com.memory.mina.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class MinaServerKeepAliveMessage implements KeepAliveMessageFactory {
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
        //return HEARTBEATREQUEST;
        return null;
    }
    public Object getResponse(IoSession ioSession, Object o) {
        String consumerTag = new StringBuffer(ioSession.getAttribute("type")
                + "-" + ioSession.getAttribute("uid")).toString();
        System.out.println("heartbeat: "+ consumerTag);
        return HEARTBEATRESPONSE;
    }
}
