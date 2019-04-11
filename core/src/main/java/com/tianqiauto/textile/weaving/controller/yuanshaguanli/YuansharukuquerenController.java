package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

/**
 * @Author bjw
 * @Date 2019/4/2 16:06
 */

import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuansharukuquerenServer;
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
 * @Date 2019/3/30 11:04
 */
@RestController
@RequestMapping("yuanshaguanli/yuansharukuqueren")
public class YuansharukuquerenController {

    @Autowired
    private YuansharukuquerenServer yuansharukuquerenServer;

    @GetMapping("query_page")
    public Result findAll(YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing,@PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuansharukuquerenServer.findAll(yuanSha_ruKu_shenqing,pageable));
    }

    @PostMapping("dengji")
    @Logger(msg = "原纱申请确认-原纱入库申请信息确认")
    @ApiOperation("原纱申请确认-原纱入库申请信息确认")
    public Result update(@RequestBody YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing) {
        YuanSha_RuKu_Shenqing shenqing = yuansharukuquerenServer.dengji(yuanSha_ruKu_shenqing);
        return Result.ok("成功！", shenqing);
    }



}
