package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshachukushenqingServer;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuansharukushenqingServer;
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
 * @Date 2019/4/11 17:44
 */
@RestController
@RequestMapping("yuanshaguanli/yuanshachukushenqing")
public class YuanshachukushenqingController {

    @Autowired
    private YuanshachukushenqingServer yuanshachukushenqingServer;



    @GetMapping("query_page")
    public Result findAll(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuanshachukushenqingServer.findAll(yuanSha_chuKu_shenqing,pageable));
    }

//    @PostMapping("add")
//    @Logger(msg = "原纱入库申请添加申请")
//    @ApiOperation("原纱管理-原纱入库申请添加申请")
//    @ResponseBody
//    public Result add(@RequestBody YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing){
//        yuanSha_ruKu_shenqing = yuansharukushenqingServer.save(yuanSha_ruKu_shenqing);
//        return Result.ok("添加成功！",yuanSha_ruKu_shenqing);
//    }
//
//    @GetMapping("delete")
//    @Logger(msg = "原纱申请-根据id删除信息")
//    @ApiOperation("原纱申请-根据id删除信息")
//    public Result delete(Long id) {
//        yuansharukushenqingServer.deleteById(id);
//        return Result.ok("删除成功！", id);
//    }
//
//    @PostMapping("update")
//    @Logger(msg = "原纱申请-修改原纱入库申请信息")
//    @ApiOperation("原纱申请-修改原纱入库申请信息")
//    public Result update(@RequestBody YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing) {
//        YuanSha_RuKu_Shenqing shenqing = yuansharukushenqingServer.update(yuanSha_ruKu_shenqing);
//        return Result.ok("修改成功！", shenqing);
//    }



}
