package com.tianqiauto.textile.weaving.mina;

import lombok.extern.java.Log;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @ClassName ClientHandler
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-03 14:34
 * @Version 1.0
 **/
@Log
public class ClientHandler extends IoHandlerAdapter {
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.severe("客户端发生异常:"+cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        System.out.println("客户端接收到数据：" + str);
    }

}
