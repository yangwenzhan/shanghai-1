package com.tianqiauto.textile.weaving.controller.jihuaguanli;

import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong;
import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong_Main;
import com.tianqiauto.textile.weaving.service.jihuaguanli.ChuanzongjihuaxiadaServer;
import com.tianqiauto.textile.weaving.service.jihuaguanli.ChuanzongjihuazhixingServer;
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
@RequestMapping("jihuaguanli/chuanzongjihuazhixing")
public class ChuanzongjihuazhixingController {

    @Autowired
    private ChuanzongjihuazhixingServer chuanzongjihuazhixingServer;

    @GetMapping("query_page")
    public Result findAll(JiHua_ChuanZong jiHuaChuanZong, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chuanzongjihuazhixingServer.findAll(jiHuaChuanZong,pageable));
    }


    @GetMapping("getZhizhou")
    @Logger(msg = "计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
    @ApiOperation("计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
    public Result getZhizhou(String heyuehao_id) {
        List<Map<String, Object>> list = chuanzongjihuazhixingServer.getZhizhou(heyuehao_id);
        return Result.ok("查询成功！", list);
    }

    @PostMapping("update")
    @Logger(msg = "计划管理-穿综计划执行")
    @ApiOperation("计划管理-穿综计划执行")
    public Result update(@RequestBody JiHua_ChuanZong jiHuaChuanZong) {
        chuanzongjihuazhixingServer.update(jiHuaChuanZong);
        return Result.ok("修改成功！", jiHuaChuanZong);
    }


}
