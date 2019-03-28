package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job.analysis;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.socket.Client;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 提高效率，一切为了单例
 * @Date 2019/3/7 10:22 bjw
 */
@Slf4j
public abstract class AbstractAnalysis{

    /**
     * 初始化Bean，并启动线程。
     **/
    public void init(List<PicanolHost> picanolHostList){
        //---------------------------------------------------------------------------启动线程
        Runnable run = () -> {
            //1.设置发送报文
            PCN requestPcn = setRequestPcn();
            //2.循环发送
            for(PicanolHost host:picanolHostList){
                requestPcn.getHeader().setDestinationId(host.getMachineNumber());//fixme 上线考虑
                PCN pcn = send(host.getIp(),host.getPort(),requestPcn);
                //3.解析报文放入获取Param放到List容器中。
                analysisPcn(pcn);
            }
        };
        new Thread(run).start();//一切为了单例
        //---------------------------------------------------------------------------启动线程
    }

    /**
     * 设置请求内容
     * @Date 2019/3/7 11:00
     **/
    private PCN setRequestPcn(){
        PCN requestPcn = new PCN();
        requestPcn.getHeader().setSourceId("Bicom");        //来源识别
        requestPcn.getHeader().setMessageType("Monitoring");
        requestPcn.getHeader().setMessageCode("bicom");
        requestPcn.getHeader().setDataFormat("VDI");
        setRequestPcn(requestPcn.getBody());
        return requestPcn;
    }
    protected abstract void setRequestPcn(PCN.Body body);

    /**
     * 解析请求内容
     * @Date 2019/3/7 11:00
     **/
    abstract void analysisPcn(PCN responsePcn);

    private PCN send(String ip, int number, PCN requestPcn){
        byte[] message = Client.send(ip,number,requestPcn.toString());
        return new PCN(message);
    }
}