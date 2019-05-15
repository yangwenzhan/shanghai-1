package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job.analysis;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.ParamVo;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.BytesUtil;
import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 请求获取打纬密度 bjw
 * @Date 2019/3/7 10:38
 */
@Service
public class PCN130Thread extends AbstractAnalysis {


    /**
     * 设置报文体
     * @Date 2019/3/7 15:24
     **/
    @Override
    protected void setRequestPcn(PCN.Body body) {
        body.setId((byte)130);
        byte[] date = {(byte)20};
        body.setData(date);
    }

    /**
     * 解析报文把解析的参数放到List容器中
     * @Date 2019/3/7 15:24
     **/
    @Override
    protected void analysisPcn(PCN responsePcn,Current_BuJi currentBuJi) {
        if (null == responsePcn || responsePcn.toString().trim().length() < 1) {
            return;
        }
        String sourceId = responsePcn.getHeader().getSourceId();
        byte[] data = responsePcn.getBody().getData();
        byte units = data[3];
        switch (units){
            case 0:
                ParamVo.addParam(sourceId,"纬密单位","picks/cm","094"); break;
            case 1:
                ParamVo.addParam(sourceId,"纬密单位","picks/inch","094"); break;
            case 2:
                ParamVo.addParam(sourceId,"纬密单位","picks/mm","094"); break;
            default: ParamVo.addParam(sourceId,"纬密单位","","094");
        }
        byte[]  pickdensity =  Arrays.copyOfRange(data,4,6);//定长
        ParamVo.addParam(sourceId,"打纬密度",String.valueOf(BytesUtil.byteToFraction(pickdensity)),"095");
        currentBuJi.setWeimi((double)BytesUtil.byteToFraction(pickdensity));
        ParamVo.addParam(sourceId,"纬密-correction",String.valueOf(BytesUtil.bytesToShort(data[6])),"096");
    }
}
