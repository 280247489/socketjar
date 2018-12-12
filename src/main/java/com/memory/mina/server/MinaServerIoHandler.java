package com.memory.mina.server;

import com.memory.mina.entity.CloseMessage;
import com.memory.mina.entity.IMMessage;
import com.memory.mina.entity.OpenMessage;
import com.memory.mina.entity.SysMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

public class MinaServerIoHandler extends IoHandlerAdapter {

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
     }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        String consumerTag = new StringBuffer(
                session.getAttribute("type" ) + "-" +
                        session.getAttribute("uid" )).toString();
        if(!"repeat".equals(session.getAttribute("closeInfo" ))){
            doSessionClose(consumerTag);
        }
        System.out.println("sessionClosed: info: "+session.getAttribute("closeInfo" )+
                " - userId: "+consumerTag+
                " - count: "+session.getService().getManagedSessionCount()+
                " - cache: "+MinaSessionCache.getMap().size());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        String consumerTag = new StringBuffer(session.getAttribute("type")
                + "-" + session.getAttribute("uid")).toString();
        System.out.println("sessionIdle-心跳请求超时: userId: "+ consumerTag);
        session.setAttribute("closeInfo", "sessionIdle");
        session.closeOnFlush();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        String consumerTag = new StringBuffer(session.getAttribute("type")
                + "-" + session.getAttribute("uid")).toString();
        System.out.println("exceptionCaught: userId: "+ consumerTag);
        session.setAttribute("closeInfo", "exceptionCaught");
        session.closeOnFlush();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        System.out.println("messageReceived: "+ message);
        //处理客户端消息
        doMessage(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    private void doMessage(IoSession session, Object message) throws Exception {
        if(message instanceof IMMessage){
            doIMMessage((IMMessage) message);
        }else if(message instanceof OpenMessage){
            doOpenMessage(session, (OpenMessage) message);
        }else if(message instanceof CloseMessage){
            doCloseMessage(session, (CloseMessage) message);
        }else{
            System.out.println("回复");
            session.write("hello");
        }
    }

    private void doIMMessage(IMMessage message) throws Exception {
        IMMessage imMessage = message;
        System.out.println("messageReceived-imMessage");
        //发送消息
        send(imMessage);
    }

    private void send(IMMessage imMessage) {
        for (Map.Entry<String, IoSession> entry : MinaSessionCache.getMap().entrySet()){
            entry.getValue().write(imMessage);
        }
    }

    private void doOpenMessage(IoSession session, OpenMessage message) {
        OpenMessage openMessage = message;
        String consumerTag = new StringBuffer(openMessage.getType()
                + "-" + openMessage.getUid()).toString();
        if(MinaSessionCache.getMap().containsKey(consumerTag)){
            IoSession oldIoSession = MinaSessionCache.getMap().get(consumerTag);
            SysMessage sysMessage = new SysMessage();
            sysMessage.setSysType("exit");
            sysMessage.setContent("账户另一地点登录，强制退出。如非本人操作，请联系管理员！");
            oldIoSession.write(sysMessage);
            //处理关闭ioSession
            oldIoSession.setAttribute("closeInfo", "repeat");
            doSessionClose(consumerTag);
            oldIoSession.closeOnFlush();
            System.out.println("repeat login: "+ consumerTag);
        }
        session.setAttribute("uid", openMessage.getUid());
        session.setAttribute("type", openMessage.getType());
        MinaSessionCache.getMap().put(consumerTag, session);
        System.out.println("messageReceived-openMessage: userId: "+consumerTag+
                " - count: "+session.getService().getManagedSessionCount()+
                " - cache: "+MinaSessionCache.getMap().size());
    }

    private void doCloseMessage(IoSession session, CloseMessage message) {
        CloseMessage closeMessage = message;
        //处理cache关闭ioSession
        session.setAttribute("closeInfo", "closeMessage");
        session.closeOnFlush();
        System.out.println("messageReceived-close: "+ session.getAttribute("uid"));
    }

    private void doSessionClose(String consumerTag) {
        //处理cache
        MinaSessionCache.getMap().remove(consumerTag);
    }
}
