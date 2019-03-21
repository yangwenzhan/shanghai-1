package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.JiHua_JiangSha;
import com.tianqiauto.textile.weaving.service.JiangShaJiHuaService;
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
 * @Date 2019/3/20 8:32
 */
@Controller
@RequestMapping("jiangshajihua")
public class JiangShaJiHuaController {

    @Autowired
    private JiangShaJiHuaService jiangShaJiHuaService;

    @PostMapping("addJihuaJiangsha")
    @Logger(msg = "添加浆纱计划信息")
    @ApiOperation("计划浆纱-添加浆纱计划信息")
    @ResponseBody
    public Result addHeyuegao(@RequestBody JiHua_JiangSha jiHuaJiangSha){
        jiHuaJiangSha = jiangShaJiHuaService.save(jiHuaJiangSha);
        return Result.ok("添加成功！",jiHuaJiangSha);
    }

    @PostMapping("findById")
    @ApiOperation("计划浆纱-根据订单id查询浆纱计划")
    @ResponseBody
    public Result findById(Long id){
        JiHua_JiangSha jiHuaJiangSha = jiangShaJiHuaService.findById(id);
        return Result.ok("查询成功！",jiHuaJiangSha);
    }
//
//    @PostMapping("findByid")
//    @ApiOperation("合约号管理-根据id查询合约号")
//    @ResponseBody
//    public Result findByid(Long id){
//        Heyuehao heyuehao = heyuehaoService.findByid(id);
//        return Result.ok("查询成功！",heyuehao);
//    }
//

//
//    @PostMapping("deleteById")
//    @Logger(msg = "根据id删除合约号信息")
//    @ApiOperation("合约号管理-根据id删除合约号信息")
//    @ResponseBody
//    public Result deleteById(Long id){
//        heyuehaoService.deleteById(id);
//        return Result.ok("删除成功！","");
//    }
//
//    @PostMapping("update")
//    @Logger(msg = "更新合约号信息")
//    @ApiOperation("合约号管理-更新合约号信息")
//    @ResponseBody
//    public Result update(@RequestBody Heyuehao heyuehao){
//        int ret = heyuehaoService.update(heyuehao);
//        return Result.ok("更新成功！",ret);
//    }

}
