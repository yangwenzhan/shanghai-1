package com.tianqiauto.textile.weaving.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @ClassName ClientConfig
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-03 14:19
 * @Version 1.0
 **/
@Component
public class ClientConfig{





    // 定义IP地址
    private static final String HOST = "127.0.0.1";
    // 定义端口
    private static final int PORT = 9999;

    public void run() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            System.out.println("hello"+i);
            IoSession ioSession;
            // 创建一个非阻塞的客户端
            IoConnector ioConnector = new NioSocketConnector(5);
            // 设置超时时间
            ioConnector.setConnectTimeoutMillis(30000);
            // 设置编码解码器
            ioConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                    new TextLineCodecFactory(Charset.forName("UTF-8"),LineDelimiter.DEFAULT.getValue(),LineDelimiter.DEFAULT.getValue())));
            // 绑定逻辑处理类
            ioConnector.setHandler(new ClientHandler());
            // 创建连接
            ConnectFuture future = ioConnector.connect(new InetSocketAddress(HOST, PORT));
            // 等待连接创建完成
            future.awaitUninterruptibly();
            // 获取连接session
            ioSession = future.getSession();
            // 发送数据
            ioSession.write("Mina,hello你好");
            // 等待关闭连接
            ioSession.getCloseFuture().awaitUninterruptibly();
            ioConnector.dispose();
        }


    }



}
