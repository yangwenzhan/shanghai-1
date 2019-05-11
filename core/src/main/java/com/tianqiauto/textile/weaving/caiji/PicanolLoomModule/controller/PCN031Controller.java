package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.controller;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PCN;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.dispenser.AbstractBispenser;
import org.springframework.stereotype.Controller;

/**
 * @Author bjw
 * @Date 2019/3/9 17:49
 */
@Controller
public class PCN031Controller extends AbstractBispenser {

    @Override
    public void analysis(PCN request,String ip) {
        System.out.println(request.toString());
    }
}
