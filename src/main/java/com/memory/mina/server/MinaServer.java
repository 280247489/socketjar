package com.memory.mina.server;

import com.memory.mina.server.codec.MinaServerProtocolCodecFactory;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

public class MinaServer {
    private static final int PORT = 8098;
    private static final int IDELTIME = 30;
    private static final int IDELTIMEOUT = 20;
    private static final int HEARTBEATRATE = 90;
    public void startup() {
        try {
            NioSocketAcceptor acceptor = new NioSocketAcceptor();
            acceptor.setHandler(new MinaServerIoHandler());
            acceptor.getSessionConfig().setReadBufferSize(2048);
            //acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIME);
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                    new MinaServerProtocolCodecFactory()));
            //        new ObjectSerializationCodecFactory()));
            //        new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());

            KeepAliveFilter heartBeat = new KeepAliveFilter(new MinaServerKeepAliveMessage(), IdleStatus.READER_IDLE);
            //设置是否forward到下一个filter
            heartBeat.setForwardEvent(true);
            //设置心跳频率
            heartBeat.setRequestInterval(HEARTBEATRATE);
            //heartBeat.setRequestTimeout(IDELTIMEOUT);
            acceptor.getFilterChain().addLast("heartbeat", heartBeat);
            acceptor.bind(new InetSocketAddress(PORT));

            System.out.println("socket-tcp-server-start: " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
