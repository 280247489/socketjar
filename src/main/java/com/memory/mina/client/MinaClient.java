package com.memory.mina.client;

import com.memory.mina.entity.IMMessage;
import com.memory.mina.entity.OpenMessage;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

public class MinaClient {

    private static final int PORT = 8098;
    private static final int IDELTIME = 30;
    private static final int IDELTIMEOUT = 30;
    private static final int HEARTBEATRATE = 60;
    public static void main(String[] args) {
        try {
            IoConnector connector = new NioSocketConnector();
            connector.setHandler(new MinaClientHandler());
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                    new ObjectSerializationCodecFactory()));
            //connector.getFilterChain().addLast("logger", new LoggingFilter());

            KeepAliveFilter heartBeat = new KeepAliveFilter(new MinaClientKeepAliveMessage(), IdleStatus.WRITER_IDLE,
                    new MinaClientKeepAliveRequestTimeoutHandler());
            //设置是否forward到下一个filter
            heartBeat.setForwardEvent(true);
            //设置心跳频率
            heartBeat.setRequestInterval(HEARTBEATRATE);
            heartBeat.setRequestTimeout(IDELTIMEOUT);
            connector.getFilterChain().addLast("heartBeat", heartBeat);
            ConnectFuture future = connector.connect(new InetSocketAddress("::", PORT));
            future.awaitUninterruptibly();
            IoSession session = future.getSession();
            OpenMessage openMessage = new OpenMessage();
            openMessage.setUid("user1");
            openMessage.setType("mobile");
            openMessage.setDate(new Date());
            session.write(openMessage);

            boolean flag ;
            while(true){
                flag = true;
                System.out.println("single OR group ?");

                IMMessage imMessage = new IMMessage();
                imMessage.setMsgType(1);
                imMessage.setDate(new Date());
                imMessage.setFromId(openMessage.getUid());

                Scanner scan = new Scanner(System.in);
                String read = scan.nextLine();
                if(read.equals("single")){
                    imMessage.setToType(read);
                    System.out.println("To whom ?");
                    scan = new Scanner(System.in);
                    imMessage.setToId(scan.nextLine());
                    System.out.println("msg content ?");
                    scan = new Scanner(System.in);
                    imMessage.setContent(scan.nextLine());
                }else if(read.equals("group")){
                    imMessage.setToType(read);
                    imMessage.setToId("group1");
                    System.out.println("msg content ?");
                    scan = new Scanner(System.in);
                    imMessage.setContent(scan.nextLine());
                }else{
                    flag = false;
                    System.out.println("while");
                }
                if(flag){
                    session.write(imMessage);
                }
            }
        } catch (Exception e) {
            System.err.println("连接失败");
            e.printStackTrace();
        }
    }
}
