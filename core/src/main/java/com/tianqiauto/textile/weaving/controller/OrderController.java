package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.service.OrderService;
import com.tianqiauto.textile.weaving.util.Page.PageModel;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.poi.ImportUtil;
import com.tianqiauto.textile.weaving.util.poi.downloadUtils;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @Author bjw
 * @Date 2019/3/14 9:05
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/findAll_view", method = RequestMethod.GET)
    public String uploadExcel(Model model) {
        model.addAttribute(orderService.findAll());
        return "/views.order/ddgl";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @Logger(msg = "导入订单信息")
    @ApiOperation("订单管理-导入订单")
    @ResponseBody
    public Result upload(MultipartFile file) {
        try {
            ImportUtil importUtil = new ImportUtil(file);
            ImportUtil.SheetVo sheetVo = importUtil.getSheet(0);
            String bufu = sheetVo.getVal(0, 1);
            Order order = orderService.save(new Order());//fixme
            return Result.ok("新增订单成功！", order);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("导入文件异常！", e);
        }
    }


    @RequestMapping(value = "downloadExcel", method = RequestMethod.GET)
    @ApiOperation("订单管理-导出订单模板")
    public void downloadExcel(HttpServletResponse response) {
        Resource resource = new ClassPathResource("/templates/views.order/订单模板.xlsx");
        downloadUtils.download(response, resource);
    }


    @PostMapping("addOrder")
    @Logger(msg = "添加订单信息")
    @ApiOperation("订单管理-添加订单")
    @ResponseBody
    public Result addOrder(@Valid @RequestBody Order order) {
        order = orderService.save(order);
        return Result.ok("新增订单成功！", order);
    }

    @PostMapping("findAll")
    @ApiOperation("订单管理-查询订单")
    @ResponseBody
    public Result findAll(@RequestBody PageModel<Order> order) {
        return Result.ok("查询成功！", order);
    }

    @GetMapping("deleteOrder")
    @Logger(msg = "撤销订单信息")
    @ApiOperation("订单管理-撤销订单")
    @ResponseBody
    public Result deleteOrder(Long id) {
        orderService.deleteById(id);
        return Result.ok("查询成功！", id); //fixme 有可能是假删除
    }

    @PostMapping("updateOrder")
    @Logger(msg = "修改订单信息")
    @ApiOperation("订单管理-修改订单")
    @ResponseBody
    public Result updateOrder(@RequestBody Order order) {
        orderService.update(order);
        return Result.ok("查询成功！", order);
    }

    @PostMapping("getOrder")
    @ApiOperation("订单管理-订单信息")
    public Result getOrder(Long id) {
        Order order = orderService.findByid(id);
        return Result.ok("查询成功！", order);
    }


}
