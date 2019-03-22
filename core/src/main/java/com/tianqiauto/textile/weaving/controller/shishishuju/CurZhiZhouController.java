package com.tianqiauto.textile.weaving.controller.shishishuju;

import com.tianqiauto.textile.weaving.service.CurDataService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ZhiZhouController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/cur_zhizhou")
@Api(description = "织轴实时状态")
public class CurZhiZhouController {

    @Autowired
    private CurDataService curDataService;

    @GetMapping("cur_zhizhou")
    @ApiOperation(value = "织轴实时状态查询")
    public Result cur_zhizhou(String zt_id, String heyuehao, String zhouhao, String weizhi){
        return curDataService.cur_zhizhou(zt_id, heyuehao, zhouhao, weizhi);
    }

    @GetMapping("cur_zhizhou_hyshz")
    @ApiOperation(value = "织轴实时状态查询-按合约号汇总")
    public Result cur_zhizhou_hyshz(){
        return curDataService.cur_zhizhou_hyshz();
    }

    @GetMapping("cur_zhizhou_zthz")
    @ApiOperation(value = "织轴实时状态查询-按状态汇总")
    public Result cur_zhizhou_zthz(){
        return curDataService.cur_zhizhou_zthz();
    }

}
