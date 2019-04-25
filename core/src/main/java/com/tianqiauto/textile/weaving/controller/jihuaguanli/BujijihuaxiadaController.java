package com.tianqiauto.textile.weaving.controller.jihuaguanli;

import com.tianqiauto.textile.weaving.model.sys.JiHua_BuJi;
import com.tianqiauto.textile.weaving.service.jihuaguanli.BujijihuaxiadaServer;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author bjw
 * @Date 2019/4/24 8:47
 */
@RestController
@RequestMapping("jihuaguanli/bujijihuaxiada")
public class BujijihuaxiadaController {

    @Autowired
    private BujijihuaxiadaServer bujijihuaxiadaServer;

    /**
     * 查询优先级
     * @Author bjw
     * @Date 2019/4/24 15:42
     **/
    @GetMapping("query_distinctYouxianji")
    public Result query_distinctYouxianji() {
        return Result.ok("查询成功！",bujijihuaxiadaServer.query_distinctYouxianji());
    }

    @GetMapping("query_page")
    public Result findAll(JiHua_BuJi jiHuaBuJi, @PageableDefault( sort = { "youxianji","id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(bujijihuaxiadaServer.findAll(jiHuaBuJi,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "计划管理")
    @ApiOperation("计划管理-布机计划下达新增")
    @ResponseBody
    public Result add(@RequestBody JiHua_BuJi jiHuaBuJi){
        jiHuaBuJi = bujijihuaxiadaServer.save(jiHuaBuJi);
        return Result.ok("添加成功！",jiHuaBuJi);
    }

    @GetMapping("delete")
    @Logger(msg = "计划管理-根据id删除布机计划")
    @ApiOperation("计划管理-根据id删除布机计划")
    public Result delete(Long id) {
        bujijihuaxiadaServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @GetMapping("getZhizhou")
    @Logger(msg = "计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
    @ApiOperation("计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
    public Result getZhizhou(String jixing_id,String heyuehao_id,String leixing_id) {
        List<Object> list =  bujijihuaxiadaServer.getZhizhou(jixing_id,heyuehao_id,leixing_id);
        return Result.ok("查询成功！", list);
    }

    @PostMapping("update")
    @Logger(msg = "计划管理-修改布机计划信息")
    @ApiOperation("计划管理-修改布机计划信息")
    public Result update(@RequestBody JiHua_BuJi jiHuaBuJi) {
        bujijihuaxiadaServer.update(jiHuaBuJi);
        return Result.ok("修改成功！", jiHuaBuJi);
    }


}
