package com.tianqiauto.textile.weaving.controller.dingdanguanli;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao_YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.service.dingdanguanli.HeYueHaoYuanShaService;
import com.tianqiauto.textile.weaving.service.dingdanguanli.YuanShaService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @Author bjw
 * @Date 2019/3/19 10:27
 */
@RestController
@RequestMapping("dingdanguanli/heyuehaoyuansha")
public class HeYueHaoYuanShaController {

    @Autowired
    private HeYueHaoYuanShaService heYueHaoYuanShaService;

    @Autowired
    private YuanShaService yuanShaService;

    @PostMapping("add")
    @Logger(msg = "添加合约号原纱信息")
    @ApiOperation("合约号原纱-添加原纱信息")
    public Result add(@RequestBody Heyuehao_YuanSha heyuehaoYuanSha){
        heyuehaoYuanSha = heYueHaoYuanShaService.save(heyuehaoYuanSha);
        return Result.ok("添加成功！",heyuehaoYuanSha);
    }

    @GetMapping("deleteById")
    @Logger(msg = "根据id删除合约号对应的原纱信息")
    @ApiOperation("合约号原纱-根据id删除合约号信息")
    public Result deleteById(Long id){
        heYueHaoYuanShaService.deleteById(id);
        return Result.ok("删除成功！","");
    }

    @PostMapping("update")
    @Logger(msg = "修改合约号对应的原纱信息")
    @ApiOperation("合约号原纱-根据id修改合约号信息")
    public Result deleteById(@RequestBody Heyuehao_YuanSha heyuehaoYuanSha){
        heyuehaoYuanSha = heYueHaoYuanShaService.update(heyuehaoYuanSha);
        return Result.ok("更新成功！",heyuehaoYuanSha);
    }

    @GetMapping("query_page")
    @ApiOperation("查询原纱信息")
    public Result findAll(YuanSha yuanSha,Pageable pageable){
        return Result.ok(yuanShaService.findAll(yuanSha,pageable));
    }

}
