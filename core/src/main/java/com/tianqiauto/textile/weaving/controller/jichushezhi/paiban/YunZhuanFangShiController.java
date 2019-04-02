package com.tianqiauto.textile.weaving.controller.jichushezhi.paiban;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi;
import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_Repository;
import com.tianqiauto.textile.weaving.service.jichushezhi.PaiBanService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName YunZhuanFangShiController
 * @Description 排班管理-运转方式
 * @Author lrj
 * @Date 2019/3/21 10:09
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/paiban/yunzhuanfangshi")
@Api(description = "排班管理-运转方式")
public class YunZhuanFangShiController {

    @Autowired
    private PaiBanService paiBanService;

    @Autowired
    private YunZhuanFangShi_Repository yunZhuanFangShi_repository;

    @GetMapping("findAllYZFS")
    @ApiOperation(value = "查询所有运转方式")
    public Result findAllYZFS(){
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<PB_YunZhuanFangShi> list = yunZhuanFangShi_repository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @PostMapping("add_new_yzfs")
    @ApiOperation(value="新增运转方式",notes = "json对象和数组：conditions")
    public Result add_new_yzfs(@RequestBody PB_YunZhuanFangShi paibanRole){
        paiBanService.add_new_yzfs(paibanRole);
        return Result.ok("新增成功!",true);
    }

    @PostMapping("upd_yzfs_Info")
    @ApiOperation(value="修改运转方式的排班信息")
    public Result upd_yzfs_Info(@RequestBody PB_YunZhuanFangShi paibanRole){
        paiBanService.upd_yzfs_Info(paibanRole);
        return Result.ok("修改成功!",true);
    }

    @GetMapping("findAllById")
    @ApiOperation(value = "根据运转方式id查询")
    public Result findAllById(Long id){
        PB_YunZhuanFangShi yunZhuanFangShi = yunZhuanFangShi_repository.findAllById(id);
        return Result.ok("查询成功",yunZhuanFangShi);
    }

}
