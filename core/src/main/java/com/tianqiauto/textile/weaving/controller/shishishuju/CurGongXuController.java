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
 * @ClassName CurBuJiController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/16 14:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/cur_gongxu")
@Api(description = "分工序实时数据总览")
public class CurGongXuController {

    @Autowired
    private CurDataService curDataService;

    @GetMapping("cur_buji")
    @ApiOperation(value = "查询布机实时数据")
    public Result cur_buji(String jx_id,String hyh_id,String yxzt_id,String online){
        return curDataService.cur_buji(jx_id, hyh_id, yxzt_id, online);
    }

    @GetMapping("findHisDataBtn")
    @ApiOperation(value = "查询历史曲线按钮")
    public Result findHisDataBtn(String jt_id){
        return curDataService.findHisDataBtn(jt_id);
    }

    @GetMapping("queryXxcsByJtid")
    @ApiOperation(value = "根据机台id查询详情参数")
    public Result queryXxcsByJtid(String jt_id){
        return curDataService.queryXxcsByJtid(jt_id);
    }

    @GetMapping("queryXxcs_curBuji")
    @ApiOperation(value = "根据机台id查询详细参数--从布机实时表中查询")
    public Result queryXxcs_curBuji(String jt_id){
        return curDataService.queryXxcs_curBuji(jt_id);
    }

    @GetMapping("queryBjcsByJtid")
    @ApiOperation(value = "根据机台id查询报警参数")
    public Result queryBjcsByJtid(String jt_id){
        return curDataService.queryBjcsByJtid(jt_id);
    }

    @GetMapping("cur_chejianzonglan")
    @ApiOperation(value = "查询车间总览数据")
    public Result cur_chejianzonglan(){
        return curDataService.cur_chejianzonglan();
    }

    @GetMapping("cur_zhengjing")
    @ApiOperation(value = "查询整经实时总览数据")
    public Result cur_zhengjing(){
        return curDataService.cur_zhengjing();
    }

    @GetMapping("cur_jiangsha")
    @ApiOperation(value = "查询浆纱实时总览数据")
    public Result cur_jiangsha(){
        return curDataService.cur_jiangsha();
    }

    @GetMapping("cur_chuanzong")
    @ApiOperation(value = "查询穿综实时总览数据")
    public Result cur_chuanzong(){
        return curDataService.cur_chuanzong();
    }

    @GetMapping("cur_liaojiyuce")
    @ApiOperation(value = "布机了机预测")
    public Result cur_liaojiyuce(){
        return curDataService.cur_liaojiyuce();
    }

    @GetMapping("cur_luobuyuce")
    @ApiOperation(value = "布机落布预测")
    public Result cur_luobuyuce(){
        return curDataService.cur_luobuyuce();
    }



}


