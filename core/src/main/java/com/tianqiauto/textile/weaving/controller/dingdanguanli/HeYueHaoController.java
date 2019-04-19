package com.tianqiauto.textile.weaving.controller.dingdanguanli;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao_YuanSha;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.service.dingdanguanli.HeyuehaoService;
import com.tianqiauto.textile.weaving.service.dingdanguanli.OrderService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String heyuehao(Model model,Long id) {
        Order order = new Order();
        if(null != id){
           order = orderService.findByid(id);
        }
        model.addAttribute("order", order);
        return "/views/dingdanguanli/heyuehao";
    }

    @RequestMapping("/heyuehao_page")
    public String heyuehao_page() {
        return "/views/heyuehaoguanli/heyuehaoguanli";
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

        List<Heyuehao> list = heyuehaoService.findByName(heyuehao.getName());
        if(list.size()>0){
            return Result.result(666,"合约号"+heyuehao.getName()+"已存在不能重复添加！",heyuehao);
        }

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
        List<Heyuehao> list = heyuehaoService.findByName(heyuehao.getName());
        if(list.size()>0){
            return Result.result(666,"合约号"+heyuehao.getName()+"已存在不能修改！",heyuehao);
        }
        int ret = heyuehaoService.update(heyuehao);
        heyuehao = heyuehaoService.findByid(heyuehao.getId());
        return Result.ok("更新成功！",heyuehao);
    }

    @GetMapping("create_heyuehao")
    @ApiOperation("合约号管理-生成新的合约号")
    @ResponseBody
    public Result create_heyuehao(String order_id, String flag){
        Object ret = heyuehaoService.create_heyuehao(order_id,flag);
        return Result.ok("成功！",ret);
    }

    @GetMapping("findAllPage")
    @ApiOperation("合约号管理-查询所有的合约号")
    @ResponseBody
    public Result findAllPage(Heyuehao heyuehao,Pageable pageable){
        Object ret = heyuehaoService.findAllPage(heyuehao,pageable);
        return Result.ok("成功！",ret);
    }

    @GetMapping("/getYuanSha")
    @ApiOperation(value = "根据订单id查询对应的经纬纱信息")
    @ResponseBody
    public Result findByName(String type, Long id){
        Heyuehao heyuehao = heyuehaoService.findByid(id);
        Set<Heyuehao_YuanSha> set = new HashSet<>();
        if("jingsha".equals(type)){
            set = heyuehao.getJingsha();
        }else if("weisha".equals(type)){
            set = heyuehao.getWeisha();
        }
        return Result.ok(set);
    }

    @GetMapping("findAll")
    @ApiOperation("合约号管理-查询所有的合约号信息")
    @ResponseBody
    public Result findAll(){
        return Result.ok("成功！",heyuehaoService.findAll());
    }

}
