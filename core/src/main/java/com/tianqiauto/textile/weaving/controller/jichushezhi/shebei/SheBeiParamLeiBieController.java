package com.tianqiauto.textile.weaving.controller.jichushezhi.shebei;

import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import com.tianqiauto.textile.weaving.repository.SheBeiParamLeiBieRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有设备数据类别")
    public List<Param_LeiBie> findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "xuhao");
        List<Param_LeiBie> list = sheBeiParamLeiBieRepository.findAll(sort);
        return list;
    }

    @PostMapping("updParamLeiBie")
    @ApiOperation(value = "修改设备数据类别")
    public Result updParamLeiBie(@RequestBody Param_LeiBie param_leiBie){
        boolean flag = sheBeiParamLeiBieRepository.existsByName(param_leiBie.getName());
        if(!flag){
            sheBeiParamLeiBieRepository.updParamLeiBie(param_leiBie.getName(),param_leiBie.getXuhao(),param_leiBie.getId());
            return Result.ok("修改设备数据类别成功！",param_leiBie);
        }else{
            return Result.error("该数据类别已存在！",param_leiBie);
        }
    }

    @PostMapping("addParamLeibie")
    @ApiOperation(value = "新增设备数据类别")
    public Result addParamLeibie(@RequestBody Param_LeiBie param_leiBie){
        boolean flag = sheBeiParamLeiBieRepository.existsByName(param_leiBie.getName());
        if(!flag){
            sheBeiParamLeiBieRepository.save(param_leiBie);
            return Result.ok("新增设备数据类别成功！",param_leiBie);
        }else{
            return Result.error("该数据类别已存在！",param_leiBie);
        }
    }


}
