package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.controller;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.ParamVo;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.dao.repository.PicanolHostRepository;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.BytesUtil;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.StringUtils;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser.AbstractBispenser;
import com.tianqiauto.textile.weaving.model.sys.BuGun;
import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
import com.tianqiauto.textile.weaving.repository.BugunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Date;

/**
 * 生产状态信息
 *  bjw
 * @Date 2019/3/6 14:24
 */
@Controller
public class PCN010Controller extends AbstractBispenser {

    @Autowired
    private BugunRepository bugunRepository;

    @Autowired
    private PicanolHostRepository picanolHostRepository;

    @Override
    public void analysis(PCN request,String ip){
        PCN.Body body = request.getBody();
        String sourceId = request.getHeader().getSourceId();
        byte cnt = body.getCnt();
        //机器运行状态信息
        byte[] bitArray = BytesUtil.toBitArray(body.getData()[0]);
        ParamVo.addParam(sourceId,"生产状态",String.valueOf(bitArray[7]),"001");
        ParamVo.addParam(sourceId,"纬纱停车",String.valueOf(bitArray[6]),"002");
        ParamVo.addParam(sourceId,"经纱停车",String.valueOf(bitArray[5]),"003");
        ParamVo.addParam(sourceId,"紧急停止",String.valueOf(bitArray[4]),"004");
        ParamVo.addParam(sourceId,"手动停止",String.valueOf(bitArray[3]),"005");
        ParamVo.addParam(sourceId,"picks_1000",String.valueOf(bitArray[2]),"006");
        if(cnt > 1){//包含其他信息
            if(body.getData()[1] == 30 && body.getData()[2] == 3){//达到预选（布卷长度）时机器发送的消息 落布的消息
                switch (body.getData()[3]){
                    case 1:
                        ParamVo.addParam(sourceId,"落布长度单位","picks","007"); break;
                    case 2:
                        ParamVo.addParam(sourceId,"落布长度单位","meters","007"); break;
                    case 3:
                        ParamVo.addParam(sourceId,"落布长度单位","yards","007"); break;
                    case 5:
                        ParamVo.addParam(sourceId,"落布长度单位","cm","007"); break;
                    case 6:
                        ParamVo.addParam(sourceId,"落布长度单位","inch","007"); break;
                    case 7:
                        ParamVo.addParam(sourceId,"落布长度单位","jacquard patterns","007"); break;
                    case 8:
                        ParamVo.addParam(sourceId,"落布长度单位","dobby patterns","007"); break;
                    case 9:
                        ParamVo.addParam(sourceId,"落布长度单位","jacquard patterns","007"); break;
                    default: ParamVo.addParam(sourceId,"落布长度单位","","007");
                }
                byte[] clothLength = Arrays.copyOfRange(body.getData(),4,8);
                long buchang = BytesUtil.bytesToLongWord(clothLength);
                String luobushijian = StringUtils.NewDateToString("yyyy-MM-dd HH:mm ss");
                ParamVo.addParam(sourceId,"落布布长", String.valueOf(buchang),"008");
                ParamVo.addParam(sourceId,"落布时间", luobushijian,"009");
                insertBugun(ip,buchang);
            }
        }
    }

    private void insertBugun(String ip, long buchang){
        PicanolHost picanolHost = picanolHostRepository.findByIp(ip);
        Current_BuJi currentBJ = picanolHost.getCurrentBuJi();
        BuGun buGun = new BuGun();
//        buGun.setBanci();
        buGun.setChangdu((double)buchang);
        buGun.setHeyuehao(currentBJ.getHeyuehao());
        buGun.setJitaihao(currentBJ.getJitaihao());
        buGun.setLuoburen(currentBJ.getDangchegong());
        buGun.setLuobushijian(new Date());
        buGun.setRiqi(new Date());
        buGun.setShedingchangdu(currentBJ.getShedingbuchang());
//        buGun.set Fixme 织轴问题

    }

}
