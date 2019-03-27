package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;

/**
 *  报文解析接口-bjw
 * @Date 2019/3/6 14:31
 */
public abstract class AbstractBispenser {

    public void run(PCN pcn){
        analysis(pcn);
    }

    public abstract void analysis(PCN request);

}
