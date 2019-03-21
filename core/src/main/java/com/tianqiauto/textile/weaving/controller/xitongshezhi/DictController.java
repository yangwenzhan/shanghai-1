package com.tianqiauto.textile.weaving.controller.xitongshezhi;

import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dict
 * @Description 数据字典
 * @Author xingxiaoshuai
 * @Date 2019-03-16 17:50
 * @Version 1.0
 **/

@RestController
@RequestMapping("xitongshezhi/shujuzidian")
public class DictController {


    @Autowired
    private Dict_TypeRepository dict_typeRepository;


    @GetMapping("query_page")
    public Result query_page(Pageable pageable){

        return Result.ok(dict_typeRepository.findAll(pageable));
    }



    @GetMapping("edit")
    public Result edit(Dict_Type dict_type){

        Dict_Type former =  dict_typeRepository.getOne(dict_type.getId());

        former.setCode(dict_type.getCode());
        former.setName(dict_type.getName());

        former.setFixed(dict_type.getFixed());


        return Result.ok("修改成功",dict_typeRepository.save(former));

    }

    @GetMapping("edit_fixed")
    public Result edit(String id,String value){

        Dict_Type former =  dict_typeRepository.getOne(Long.parseLong(id.trim()));

        former.setFixed(Integer.parseInt(value));

        return Result.ok("修改成功",dict_typeRepository.save(former));

    }


}
