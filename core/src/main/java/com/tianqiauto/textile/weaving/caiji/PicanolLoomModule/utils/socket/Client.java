package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端请求 bjw
 * @Date 2019/1/21 11:03
 */
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

}
