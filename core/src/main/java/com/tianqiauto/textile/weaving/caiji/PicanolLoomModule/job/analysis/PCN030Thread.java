package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job.analysis;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.ParamVo;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.BytesUtil;
import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 打纬次数及布长 bjw
 * @Date 2019/3/7 10:38
 */
@Service
public class PCN030Thread extends AbstractAnalysis {

    /**
     * 设置报文体
     * @Date 2019/3/7 15:24
     **/
    @Override
    protected void setRequestPcn(PCN.Body body) {
        body.setId((byte)30);
        byte[] date = {(byte)3};
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
        byte preselectionUnits = data[1];
        switch (preselectionUnits){
            case 1:
                ParamVo.addParam(sourceId,"当前布长单位","picks","010"); break;
            case 2:
                ParamVo.addParam(sourceId,"当前布长单位","meters","010"); break;
            case 3:
                ParamVo.addParam(sourceId,"当前布长单位","yards","010"); break;
            case 7:
                ParamVo.addParam(sourceId,"当前布长单位","jacquard patterns","010"); break;
            case 8:
                ParamVo.addParam(sourceId,"当前布长单位","dobby patterns","010"); break;
            case 9:
                ParamVo.addParam(sourceId,"当前布长单位","color patterns","010"); break;
            default: ParamVo.addParam(sourceId,"当前布长单位","","010");
        }
        byte[]  setPreselection =  Arrays.copyOfRange(data,2,6);//定长
        long dingchang = BytesUtil.byteToDe(setPreselection);
        ParamVo.addParam(sourceId,"当前布辊定长",String.valueOf(dingchang),"011");
        currentBuJi.setShedingbuchang((double)dingchang);
        byte[]  clothLength =  Arrays.copyOfRange(data,6,10);//定长
        long buchang = BytesUtil.byteToDe(clothLength);
        ParamVo.addParam(sourceId,"当前布辊长度",String.valueOf(buchang),"012");
        currentBuJi.setBuchang((double)buchang);
    }

}
