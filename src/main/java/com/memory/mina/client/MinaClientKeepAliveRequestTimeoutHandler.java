package com.memory.mina.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class MinaClientKeepAliveRequestTimeoutHandler implements KeepAliveRequestTimeoutHandler {
    public void keepAliveRequestTimedOut(KeepAliveFilter keepAliveFilter, IoSession ioSession) throws Exception {
        ioSession.closeNow();
        System.out.println("client-心跳请求超时，主动与服务端断开连接");
    }
}
