package com.memory.mina.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class MinaServerKeepAliveRequestTimeoutHandler implements KeepAliveRequestTimeoutHandler {
    public void keepAliveRequestTimedOut(KeepAliveFilter keepAliveFilter, IoSession ioSession) throws Exception {
        ioSession.closeNow();
        System.out.println("心跳请求超时，断开连接");
    }
}
