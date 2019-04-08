package com.tianqiauto.textile.weaving.controller.jichushezhi.daping;

import com.tianqiauto.textile.weaving.model.sys.TV_ZhanShiYeMian;
import com.tianqiauto.textile.weaving.repository.ZhanShiYeMianRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ZhanShiYeMianController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/29 17:06
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/daping/zhanshiyemian")
@Api(description = "大屏管理展示页面")
public class ZhanShiYeMianController {

    @Autowired
    private ZhanShiYeMianRepository zhanShiYeMianRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有展示页面")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        List<TV_ZhanShiYeMian> zhanShiYeMians = zhanShiYeMianRepository.findAll(sort);
        return Result.ok("查询成功",zhanShiYeMians);
    }

    @PostMapping("addZSYM")
    @ApiOperation(value = "修改展示页面")
    public Result addZSYM(@RequestBody TV_ZhanShiYeMian zhanShiYeMian){
        boolean flag = zhanShiYeMianRepository.existsByName(zhanShiYeMian.getName());
        if(flag){
            return Result.result(666,"该名称已存在",zhanShiYeMian);
        }else{
            zhanShiYeMianRepository.save(zhanShiYeMian);
            return Result.ok("新增成功!",zhanShiYeMian);
        }
    }

    @PostMapping("updZSYM")
    @ApiOperation(value = "修改展示页面")
    public Result updZSYM(@RequestBody TV_ZhanShiYeMian zhanShiYeMian){
        zhanShiYeMianRepository.updZSYM(zhanShiYeMian.getSort(),zhanShiYeMian.getTingliushichang(),zhanShiYeMian.getId());
        return Result.ok("修改成功!",zhanShiYeMian);
    }

    @PostMapping("delZSYM")
    @ApiOperation(value = "删除展示页面")
    public Result delZSYM(@RequestBody TV_ZhanShiYeMian zhanShiYeMian){
        zhanShiYeMianRepository.delete(zhanShiYeMian);
        //fixme
        //将电视展示方案中也删掉该页面
        return Result.ok("删除成功!",zhanShiYeMian);
    }


}
