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
    @ApiOperation(value = "查询设备数据参数")
    public Result findAll(String gx_id, String jx_id, String cslb_id){
        return sheBeiParamService.findAll(gx_id, jx_id, cslb_id);
    }

    @GetMapping("updSheBeiParam")
    @ApiOperation(value = "修改设备参数")
    public Result updSheBeiParam(@RequestBody Param param){
        int num = sheBeiParamService.updSheBeiParam(param);
        return Result.ok("修改设备参数成功!",num);
    }

    @GetMapping("updSheBeiParam_flag")
    @ApiOperation(value = "修改是否展示报警历史曲线")
    public Result updSheBeiParam_flag(String id, String name, String val){
        int num = sheBeiParamService.updSheBeiParam_flag(id, name, val);
        return Result.ok("修改成功!",num);
    }

    @PostMapping("updSheBeiParam_Batch")
    @ApiOperation(value = "批量修改设备参数",notes = "输出参数   1表示成功   0 表示失败")
    public Result updSheBeiParam_Batch (String idStr, String sfbj, String sfzs, String sfjlqx, String dw,
                                        String cslb_id, String ccsc, String cczq){
        return sheBeiParamService.updSheBeiParam_Batch(idStr, sfbj, sfzs, sfjlqx, dw, cslb_id, ccsc, cczq);
        //fixme
        //调用存储过程批量修改，返回0失败，1成功

    }


}
