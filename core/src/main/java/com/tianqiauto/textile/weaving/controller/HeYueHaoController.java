package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.service.HeyuehaoService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/11 16:17
 */
@Controller
@RequestMapping("heyuehao")
public class HeYueHaoController {

    @Autowired
    private HeyuehaoService heyuehaoService;

    @PostMapping("findByOrderid")
    @ApiOperation("合约号管理-根据订单id查询合约号")
    @ResponseBody
    public Result findByOrderid(@RequestBody Order order){
        List<Heyuehao> list = heyuehaoService.findByOrderid(order);
        return Result.ok("查询成功！",list);
    }

    @PostMapping("findByid")
    @ApiOperation("合约号管理-根据id查询合约号")
    @ResponseBody
    public Result findByid(Long id){
        Heyuehao heyuehao = heyuehaoService.findByid(id);
        return Result.ok("查询成功！",heyuehao);
    }

    @PostMapping("addHeyuegao")
    @Logger(msg = "添加合约号信息")
    @ApiOperation("合约号管理-添加合约号信息")
    @ResponseBody
    public Result addHeyuegao(@RequestBody Heyuehao heyuehao){
        heyuehao = heyuehaoService.save(heyuehao);
        return Result.ok("添加成功！",heyuehao);
    }

    @PostMapping("deleteById")
    @Logger(msg = "根据id删除合约号信息")
    @ApiOperation("合约号管理-根据id删除合约号信息")
    @ResponseBody
    public Result deleteById(Long id){
        heyuehaoService.deleteById(id);
        return Result.ok("删除成功！","");
    }

    @PostMapping("update")
    @Logger(msg = "更新合约号信息")
    @ApiOperation("合约号管理-更新合约号信息")
    @ResponseBody
    public Result update(@RequestBody Heyuehao heyuehao){
        int ret = heyuehaoService.update(heyuehao);
        return Result.ok("更新成功！",ret);
    }
}
