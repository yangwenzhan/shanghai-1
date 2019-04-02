package com.tianqiauto.textile.weaving.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ForwardController
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-12 08:25
 * @Version 1.0
 **/
@Controller
public class ForwardController {

    @RequestMapping("forward/{path1}/{path2}")
    public String forward(@PathVariable("path1") String path1,@PathVariable("path2") String path2){
        return "views/"+path1+"/"+path2;
    }

}
