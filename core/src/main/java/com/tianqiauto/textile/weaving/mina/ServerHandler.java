package com.tianqiauto.textile.weaving.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;

/**
 * @ClassName ServerHandler
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-02 19:46
 * @Version 1.0
 **/
public class ServerHandler extends IoHandlerAdapter {


    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        System.out.println("Message -->"  + str);
        if (str.trim().equalsIgnoreCase("quit")) {
            session.close();
            return;
        }
        Date date = new Date();
        // 返回数据
        session.write(date.toString());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//        System.out.println("IDLE"+session.getId()+"  " + session.getIdleCount(status));
    }

}
