package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.socket;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author bjw
 * @Date 2019/3/9 8:55
 */
@Component
@Slf4j
public class Server {

    @Value("${Picanol.server-port:8004}")
    private int serverPort;

    public void run(){
        log.info("必佳乐织机采集程序启动中...");
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            log.info("必佳乐织机采集程序启动成功,端口号：【"+serverPort+"】。");
            while (true) {
                Socket socket = serverSocket.accept();
                SpringUtil.getBean(ServiceThread.class).init(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("必佳乐织机采集程序启动失败",e);
        }
    }
}
