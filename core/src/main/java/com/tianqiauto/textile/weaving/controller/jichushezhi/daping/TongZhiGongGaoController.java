package com.tianqiauto.textile.weaving.controller.jichushezhi.daping;

import com.tianqiauto.textile.weaving.model.sys.TV_TongZhiGongGao;
import com.tianqiauto.textile.weaving.repository.TongZhiGongGaoRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TongZhiGongGaoController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/29 16:59
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/daping/tongzhigonggao")
@Api(description = "大屏管理通知公告")
public class TongZhiGongGaoController {

    @Autowired
    private TongZhiGongGaoRepository tongZhiGongGaoRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有通知公告")
    public Result findAll(@PageableDefault(size = 20,page = 0,sort = "name,asc") Pageable pageable){
        List<TV_TongZhiGongGao> list = tongZhiGongGaoRepository.findAll();
        return Result.ok("查询成功",list);
    }

    @PostMapping("addTZGG")
    @ApiOperation(value = "新增通知公告")
    public Result addTZGG(@RequestBody TV_TongZhiGongGao tongZhiGongGao){
        boolean flag = tongZhiGongGaoRepository.existsByName(tongZhiGongGao.getName());
        if(flag){
            return Result.result(666,"该名称已存在",tongZhiGongGao);
        }else{
            tongZhiGongGaoRepository.save(tongZhiGongGao);
            return Result.ok("新增成功!",tongZhiGongGao);
        }
    }

    @PostMapping("updTZGG")
    @ApiOperation(value = "修改通知公告")
    public Result updTZGG(String neirong,Long id){
        tongZhiGongGaoRepository.updTongZhiGongGao(neirong,id);
        return Result.ok("修改成功!",id);
    }


}
