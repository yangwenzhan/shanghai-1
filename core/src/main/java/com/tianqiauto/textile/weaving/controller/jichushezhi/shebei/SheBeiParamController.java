package com.tianqiauto.textile.weaving.controller.jichushezhi.shebei;

import com.tianqiauto.textile.weaving.model.sys.Param;
import com.tianqiauto.textile.weaving.service.jichushezhi.SheBeiParamService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jichushuju/shebei/shebeiparam")
@Api(description = "设备数据参数管理")
public class SheBeiParamController {

    @Autowired
    private SheBeiParamService sheBeiParamService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询设备数据参数",notes = "工序id，机型id，参数类别id")
    public Result findAll(String gongxu, String jixing, String leiBie){
        return sheBeiParamService.findAll(gongxu, jixing, leiBie);
    }

    @PostMapping("updSheBeiParam")
    @ApiOperation(value = "修改设备参数")
    public Result updSheBeiParam(@RequestBody Param param){
        boolean flag = sheBeiParamService.existsByNameAndLeiBie(param.getName(),param.getLeiBie().getId(),param.getId());
        if(flag){
            sheBeiParamService.updSheBeiParam(param);
            return Result.ok("修改设备参数成功!",param);
        }else{
            return Result.result(666,"该参数名称已存在！",param);
        }
    }

    @GetMapping("updSheBeiParam_flag")
    @ApiOperation(value = "修改是否展示报警历史曲线")
    public Result updSheBeiParam_flag(String id, String name, String val){
        int num = sheBeiParamService.updSheBeiParam_flag(id, name, val);
        return Result.ok("修改成功!",num);
    }

    @PostMapping("updSheBeiParam_Batch")
    @ApiOperation(value = "批量修改设备参数",notes = "输出参数   1表示成功   0 表示失败")
    public Result updSheBeiParam_Batch (String idStr, String dw,
                                        String cslb_id, String ccsc, String cczq, String sfbj,String sfzs,String sfjlqx){

        //fixme
        //调用存储过程批量修改，返回0失败，1成功
        return sheBeiParamService.updSheBeiParam_Batch(idStr, dw, cslb_id, ccsc, cczq, sfbj, sfzs, sfjlqx);

    }


}
