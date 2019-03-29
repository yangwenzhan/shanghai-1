package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.SpringUtil;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 分发器----把获得的数据分发下去进行解析。
 * bjw
 * @Date 2019/1/21 14:33
 */
@Slf4j
@Component
public class Dispenser {

    @Value("${Picanol.controller-standard}")
    private String controllerStandard;

    /**
     * 利用反射执行分发
     * @Date 2019/3/6 10:02 bjw
     **/
    public PCN executionDistribution(String msg) {
        PCN requestMsg = new PCN(msg);
        short id = requestMsg.getBody().getIdToShort();
        String path = StringUtils.add0Before(3, id);
        String servlerClassName = controllerStandard.replaceAll("\\*",path);
        try {
            AbstractBispenser basBis = (AbstractBispenser) SpringUtil.getBean(servlerClassName);
            basBis.run(requestMsg);
        } catch (Exception e) {
            log.error("功能码："+path+"未找到！",e);
        }
        return getRespond(requestMsg);
    }

    /**
     * 根据请求数据分发处理后拿到响应数据返回。
     * @Date 2019/1/21 14:41
     **/
    private PCN getRespond(PCN requestMsg) {
        PCN pcn = new PCN();
        PCN.Header header = pcn.getHeader();
        header.setVersion(requestMsg.getHeader().getVersion());
        header.setSourceId(requestMsg.getHeader().getDestinationId());
        header.setDestinationId(requestMsg.getHeader().getSourceId());
        header.setMessageType(requestMsg.getHeader().getMessageType());
        header.setMessageCode(requestMsg.getHeader().getMessageCode());
        header.setDataFormat(requestMsg.getHeader().getDataFormat());
        //------------------------------------Fixme 测试返回数据
        PCN.Body body = pcn.getBody();
        body.setId(requestMsg.getBody().getId());
        byte[] data = new byte[250];
        for(int i = 0; i < data.length; i++){
            data[i] = (byte)i;
        }
        body.setData(data);
        //------------------------------------Fixme 测试返回数据
        return pcn;
    }
}
