package com.tianqiauto.textile.weaving.controller.shishishuju;

import com.tianqiauto.textile.weaving.service.shishishuju.CurDataService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName JingZhouController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/cur_jingzhou")
@Api(description = "经轴实时状态")
public class CurJingZhouController {

    @Autowired
    private CurDataService curDataService;

    @GetMapping("cur_jingzhou")
    @ApiOperation(value = "经轴实时状态查询",notes = "状态id，合约号id，轴号id")
    public Result cur_jingzhou(String zt_id, String heyuehao, String zhouhao){
        return curDataService.cur_jingzhou(zt_id, heyuehao, zhouhao);
    }

    @GetMapping("cur_jingzhou_hyshz")
    @ApiOperation(value = "经轴实时状态-按合约号汇总")
    public Result cur_jingzhou_hyshz(){
        return curDataService.cur_jingzhou_hyshz();
    }

    @GetMapping("cur_jingzhou_zthz")
    @ApiOperation(value = "经轴实时状态-按经轴状态汇总")
    public Result cur_jingzhou_zthz(){
        return curDataService.cur_jingzhou_zthz();
    }

    @GetMapping("cur_jingzhou_hyh")
    @ApiOperation(value = "经轴实时状态-查询经轴实时数据对应的合约号")
    public Result cur_jingzhou_hyh(){
        return curDataService.cur_jingzhou_hyh();
    }


}
