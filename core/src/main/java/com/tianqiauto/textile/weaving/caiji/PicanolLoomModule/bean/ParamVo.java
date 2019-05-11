package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author bjw
 * @Date 2019/3/9 11:03
 */
public class ParamVo {

    private static  Map<String, PicanolParam> map = new HashMap();

    public static void addParam(String machineNumber, String name, String value,String param_number) {
        PicanolParam parm = new PicanolParam();
        parm.setMachineNumber(machineNumber);
        parm.setName(name);
        parm.setValue(value);
        parm.setParamNumber(param_number);
        parm.setLastModifyTime(new Date());
        map.put(machineNumber+param_number,parm);
    }

    public static Collection<PicanolParam> getCollection() {
        Map<String, PicanolParam> temp = new HashMap();
        temp.putAll(map);
         return temp.values();
    }

}
