package com.tianqiauto.textile.weaving.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ForwardController
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-12 08:25
 * @Version 1.0
 **/
@Controller
public class ForwardController {


    @RequestMapping("forward/{path}")
    public String forward(@PathVariable("path") String path){
        return "views/"+path;
    }


}
