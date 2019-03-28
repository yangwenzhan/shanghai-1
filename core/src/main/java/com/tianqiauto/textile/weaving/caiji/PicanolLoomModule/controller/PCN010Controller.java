package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.controller;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.ParamVo;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.BytesUtil;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.StringUtils;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser.AbstractBispenser;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

/**
 * 生产状态信息
 *  bjw
 * @Date 2019/3/6 14:24
 */
@Controller
public class PCN010Controller extends AbstractBispenser {


    @Override
    public void analysis(PCN request){
        PCN.Body body = request.getBody();
        String sourceId = request.getHeader().getSourceId();
        byte cnt = body.getCnt();
        //机器运行状态信息
        byte[] bitArray = BytesUtil.toBitArray(body.getData()[0]);
        ParamVo.addParam(sourceId,"生产状态",String.valueOf(bitArray[7]));
        ParamVo.addParam(sourceId,"纬纱停车",String.valueOf(bitArray[6]));
        ParamVo.addParam(sourceId,"经纱停车",String.valueOf(bitArray[5]));
        ParamVo.addParam(sourceId,"紧急停止",String.valueOf(bitArray[4]));
        ParamVo.addParam(sourceId,"手动停止",String.valueOf(bitArray[3]));
        ParamVo.addParam(sourceId,"picks_1000",String.valueOf(bitArray[2]));
        if(cnt > 1){//包含其他信息
            if(body.getData()[1] == 30 && body.getData()[2] == 3){//达到预选（布卷长度）时机器发送的消息 落布的消息
                switch (body.getData()[3]){
                    case 1:
                        ParamVo.addParam(sourceId,"落布长度单位","picks"); break;
                    case 2:
                        ParamVo.addParam(sourceId,"落布长度单位","meters"); break;
                    case 3:
                        ParamVo.addParam(sourceId,"落布长度单位","yards"); break;
                    case 5:
                        ParamVo.addParam(sourceId,"落布长度单位","cm"); break;
                    case 6:
                        ParamVo.addParam(sourceId,"落布长度单位","inch"); break;
                    case 7:
                        ParamVo.addParam(sourceId,"落布长度单位","jacquard patterns"); break;
                    case 8:
                        ParamVo.addParam(sourceId,"落布长度单位","dobby patterns"); break;
                    case 9:
                        ParamVo.addParam(sourceId,"落布长度单位","jacquard patterns"); break;
                    default: ParamVo.addParam(sourceId,"落布长度单位","");
                }
                byte[] clothLength = Arrays.copyOfRange(body.getData(),4,8);
                ParamVo.addParam(sourceId,"落布布长", String.valueOf(BytesUtil.bytesToLongWord(clothLength)));
                ParamVo.addParam(sourceId,"落布时间", StringUtils.NewDateToString("yyyy-MM-dd HH:mm ss"));
            }
        }
    }


}
