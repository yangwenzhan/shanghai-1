package com.tianqiauto.textile.weaving.controller.jichushezhi.shebei;

import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import com.tianqiauto.textile.weaving.repository.SheBeiParamLeiBieRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.SheBeiParamLeiBieService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SheBeiParamLeiBieController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:27
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushuju/shebei/shebeiparamleibie")
@Api(description = "设备数据参数管理")
public class SheBeiParamLeiBieController {

    @Autowired
    private SheBeiParamLeiBieRepository sheBeiParamLeiBieRepository;

    @Autowired
    private SheBeiParamLeiBieService sheBeiParamLeiBieService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有设备数据类别",notes = "工序id，机型id")
    public Result findAll(String gongxu,String jixing,Pageable pageable){
        return Result.ok(sheBeiParamLeiBieService.findAll(gongxu, jixing, pageable));
    }

    @PostMapping("updParamLeiBie")
    @ApiOperation(value = "修改设备数据类别")
    public Result updParamLeiBie(@RequestBody Param_LeiBie param_leiBie){
        boolean flag = sheBeiParamLeiBieService.existsByGongxuAndJixingAndName(param_leiBie.getGongxu().getId(),param_leiBie.getJixing().getId(),param_leiBie.getName(),param_leiBie.getId());
        if(flag){
            sheBeiParamLeiBieRepository.updParamLeiBie(param_leiBie.getGongxu().getId(),param_leiBie.getJixing().getId(),param_leiBie.getName(),param_leiBie.getXuhao(),param_leiBie.getId());
            return Result.ok("修改设备数据类别成功！",param_leiBie);
        }else{
            return Result.result(666,"该参数类别已存在！",param_leiBie);
        }
    }

    @PostMapping("addParamLeibie")
    @ApiOperation(value = "新增设备数据类别")
    public Result addParamLeibie(@RequestBody Param_LeiBie param_leiBie){
        boolean flag = sheBeiParamLeiBieRepository.existsByGongxuAndJixingAndName(param_leiBie.getGongxu(),param_leiBie.getJixing(),param_leiBie.getName());
        if(!flag){
            sheBeiParamLeiBieRepository.save(param_leiBie);
            return Result.ok("新增设备数据类别成功！",param_leiBie);
        }else{
            return Result.result(666,"该参数类别已存在！",param_leiBie);
        }
    }


}
