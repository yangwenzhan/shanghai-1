package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.repository.RoleRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PancunyueController
 * @Description 盘存月管理
 * @Author xingxiaoshuai
 * @Date 2019-03-16 09:15
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushezhi/pancunyue")
public class PancunyueController {



    @GetMapping("query_page")
    public Result query_page(){

        List list = new ArrayList();

        Map map1 = new HashMap(); map1.put("nian","2019"); map1.put("yue","01");
        Map map2 = new HashMap(); map2.put("nian","2019"); map2.put("yue","02");

        list.add(map1);list.add(map2);

        return Result.ok(list);
    }



}
