package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.socket;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端请求 bjw
 * @Date 2019/1/21 11:03
 */
@Slf4j
public class Client {
    //ip
    //端口
    //报文 PCN

    public static byte[] send(String ip, int port, String pcn) {
        return send(ip, port, pcn.getBytes());
    }

    public static byte[]  send(String ip, int port, byte[] pcn) {
        byte[] bytes = {};
        try (
                Socket socket = new Socket(ip, port);
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bw = new BufferedWriter(outputStreamWriter);
                InputStream inputStream = socket.getInputStream();                          // 得到一个输入流，接收客户端传递的信息
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);   // 提高效率，将自己字节流转为字符流
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)      // 加入缓冲区
        ) {
            socket.setSoTimeout(15000);//15秒连接不上就放弃
            bw.write(new String(pcn));
            bw.newLine();
            bw.flush();
            socket.shutdownOutput();
            String temp;
            StringBuilder info = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                info.append(temp);
            }
            bytes = info.toString().getBytes();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("请求连接异常！");
            e.printStackTrace();
        }
        return bytes;
    }


    public static byte[] sendMessage(String ip, int port,PCN pcn) {
        byte[] request = pcn.toString().getBytes();
        byte[] res = null;
        try (
                Socket socket = new Socket(ip, port);
                InputStream is = socket.getInputStream();
                OutputStream os =  socket.getOutputStream();
        ){
            socket.setSoTimeout(15000);//15秒连接不上就放弃
            os.write(request);
            os.flush();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            do {
                count = is.read(buffer);
                bos.write(buffer, 0, count);
            } while (is.available() != 0);
            res = bos.toByteArray();
            log.error("【IP："+ip+":"+port+"】"+pcn.getBody().getId()+"请求成功！");
        } catch (UnknownHostException e) {
           log.error("主机连接异常：【IP："+ip+":"+port+"】"+e.getMessage());
        } catch (IOException e) {
            if(e.getMessage().indexOf("Connection timed out: connect") != -1){
                log.error("【IP："+ip+":"+port+"】"+pcn.getBody().getId()+"链接超时！"+e.getMessage());
            }else{
                log.error("【IP："+ip+":"+port+"】"+pcn.getBody().getId()+"数据异常！"+e.getMessage());
            }
        }
        return res;
    }


}
