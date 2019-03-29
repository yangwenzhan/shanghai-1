package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.socket;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser.Dispenser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

/**
 * Socket多线程处理类 用来处理服务端接收到的客户端请求（处理Socket对象）
 */
@Slf4j
@Component
public class ServiceThread {

    @Autowired
    private Dispenser dispenser;

    public void init(Socket socket){
        Runnable run = () -> {
            execute(socket);
        };
        new Thread(run).start();//一切为了单例
    }

    public void execute(Socket socket) {
        try (
                InputStream inputStream = socket.getInputStream();                          // 得到一个输入流，接收客户端传递的信息
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);   // 提高效率，将自己字节流转为字符流
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);      // 加入缓冲区
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bw = new BufferedWriter(outputStreamWriter)
        ) {
            String temp;
            StringBuilder info = new StringBuilder();
            while (null != (temp = bufferedReader.readLine())) {
                info.append(temp);
            }
            //log.info("服务端接收数据："+info);
            PCN pcn = dispenser.executionDistribution(info.toString());  //执行分发  一切为了单例
            //log.info("服务端响应数据："+pcn);
            bw.write(pcn.toString());
            bw.newLine();
            bw.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            log.error("服务端启动异常：",e);
            e.printStackTrace();
        }
    }
}