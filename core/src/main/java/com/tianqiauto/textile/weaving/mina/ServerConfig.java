package com.tianqiauto.textile.weaving.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MinaConfig
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-02 19:41
 * @Version 1.0
 **/


//@Configuration
public class ServerConfig {

    private static final int PORT = 9999;

    private static final int POOL_SIZE = 100;

    ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

    @Bean
    public IoHandler ioHandler() {
        return new ServerHandler();
    }
    @Bean
    public InetSocketAddress inetSocketAddress() {
        return new InetSocketAddress(PORT);
    }

    @Bean
    public IoAcceptor ioAcceptor() throws Exception {
        IoAcceptor acceptor=new NioSocketAcceptor(4);
//        acceptor.getFilterChain().addLast( "logger", loggingFilter() );
        acceptor.getFilterChain().addLast("executor",new ExecutorFilter(executor));
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"),LineDelimiter.DEFAULT.getValue(),LineDelimiter.DEFAULT.getValue())));
        acceptor.setHandler(ioHandler());
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind(inetSocketAddress());
        System.out.println("Mina服务已启动，端口：" + PORT);
        return acceptor;
    }


}
