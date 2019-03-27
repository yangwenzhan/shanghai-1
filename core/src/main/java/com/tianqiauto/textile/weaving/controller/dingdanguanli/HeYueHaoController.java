package com.tianqiauto.textile.weaving.controller.dingdanguanli;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.service.HeyuehaoService;
import com.tianqiauto.textile.weaving.service.OrderService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/11 16:17
 */
@Controller
@RequestMapping("dingdanguanli/heyuehaoguanli")
public class HeYueHaoController {

    @Autowired
    private HeyuehaoService heyuehaoService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/heyuehao")
    public String loginError(Model model,Long id) {
        Order order = new Order();
        if(null != id){
           order = orderService.findByid(id);
        }
        model.addAttribute("order", order);
        return "/views/dingdanguanli/heyuehao";
    }

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
        heyuehao = heyuehaoService.findByid(heyuehao.getId());
        return Result.ok("更新成功！",heyuehao);
    }

    @GetMapping("create_heyuehao")
    @ApiOperation("合约号管理-更新合约号信息")
    @ResponseBody
    public Result create_heyuehao(String order_id, String flag){
        Object ret = heyuehaoService.create_heyuehao(order_id,flag);
        return Result.ok("更新成功！",ret);
    }

}
