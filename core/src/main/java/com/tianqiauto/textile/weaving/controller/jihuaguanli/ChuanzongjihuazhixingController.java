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



////    @ApiOperation("计划管理-查询穿综机")
////    @GetMapping("query_chuanzongji")
////    public Result query_chuanzongji(){
////        return Result.ok(chuanzongjihuaxiadaServer.query_chuanzongji());
////    }
//
//    @PostMapping("add")
//    @Logger(msg = "计划管理")
//    @ApiOperation("计划管理-穿综下达新增")
//    @ResponseBody
//    public Result add(@RequestBody JiHua_ChuanZong_Main jiHuaChuanZongMain){
//        jiHuaChuanZongMain = chuanzongjihuaxiadaServer.save(jiHuaChuanZongMain);
//        return Result.ok("添加成功！",jiHuaChuanZongMain);
//    }
//
//    @GetMapping("delete")
//    @Logger(msg = "计划管理-根据id删除穿综计划")
//    @ApiOperation("计划管理-根据id删除穿综计划")
//    public Result delete(Long id) {
//        chuanzongjihuaxiadaServer.deleteById(id);
//        return Result.ok("删除成功！", id);
//    }
//
////    @GetMapping("getZhizhou")
////    @Logger(msg = "计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
////    @ApiOperation("计划管理-根据条件合约号id/机型id/上轴类型id来查询织轴信息")
////    public Result getZhizhou(String jixing_id,String heyuehao_id,String leixing_id) {
////        List<Object> list =  bujijihuaxiadaServer.getZhizhou(jixing_id,heyuehao_id,leixing_id);
////        return Result.ok("查询成功！", list);
////    }
////
////    @PostMapping("update")
////    @Logger(msg = "计划管理-修改布机计划信息")
////    @ApiOperation("计划管理-修改布机计划信息")
////    public Result update(@RequestBody JiHua_BuJi jiHuaBuJi) {
////        bujijihuaxiadaServer.update(jiHuaBuJi);
////        return Result.ok("修改成功！", jiHuaBuJi);
////    }


}
