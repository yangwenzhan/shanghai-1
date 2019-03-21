package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao_YuanSha;
import com.tianqiauto.textile.weaving.service.HeYueHaoYuanShaService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author bjw
 * @Date 2019/3/19 10:27
 */
@Controller
@RequestMapping("yuasha")
public class HeYueHaoYuanShaController {

    @Autowired
    private HeYueHaoYuanShaService heYueHaoYuanShaService;

    @PostMapping("add")
    @Logger(msg = "添加合约号原纱信息")
    @ApiOperation("合约号原纱-添加原纱信息")
    @ResponseBody
    public Result add(@RequestBody Heyuehao_YuanSha heyuehaoYuanSha){
        heyuehaoYuanSha = heYueHaoYuanShaService.save(heyuehaoYuanSha);
        return Result.ok("添加成功！",heyuehaoYuanSha);
    }

    @PostMapping("deleteById")
    @Logger(msg = "根据id删除合约号对应的原纱信息")
    @ApiOperation("合约号原纱-根据id删除合约号信息")
    @ResponseBody
    public Result deleteById(Long id){
        heYueHaoYuanShaService.deleteById(id);
        return Result.ok("删除成功！","");
    }

    @PostMapping("update")
    @Logger(msg = "修改合约号对应的原纱信息")
    @ApiOperation("合约号原纱-根据id修改合约号信息")
    @ResponseBody
    public Result deleteById(@RequestBody Heyuehao_YuanSha heyuehaoYuanSha){
        int ret = heYueHaoYuanShaService.update(heyuehaoYuanSha);
        return Result.ok("更新成功！",ret);
    }

}
