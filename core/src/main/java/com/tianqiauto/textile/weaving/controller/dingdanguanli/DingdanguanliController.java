package com.tianqiauto.textile.weaving.controller.dingdanguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.service.dingdanguanli.OrderService;
import com.tianqiauto.textile.weaving.service.jichushezhi.UserService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.poi.ImportUtil;
import com.tianqiauto.textile.weaving.util.poi.downloadUtils;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/14 9:05
 */
@RestController
@RequestMapping("dingdanguanli/dingdanguanli")
public class DingdanguanliController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "upload")
    @Logger(msg = "导入订单信息")
    @ApiOperation("订单管理-导入订单")
    public Result upload(MultipartFile file) throws IOException {
        ImportUtil importUtil = new ImportUtil(file);
        ImportUtil.SheetVo sheetVo = importUtil.getSheet(0);
        String bufu = sheetVo.getVal(0, 1);
        Order order = orderService.save(new Order());//fixme
        return Result.ok("新增订单成功！", order);
    }


    @GetMapping(value = "downloadExcel")
    @ApiOperation("订单管理-导出订单模板")
    public void downloadExcel(HttpServletResponse response) {
        Resource resource = new ClassPathResource("/templates/views/dingdanguanli/订单模板.xlsx");
        downloadUtils.download(response, resource);
    }


    @PostMapping("addOrder")
    @Logger(msg = "添加订单信息")
    @ApiOperation("订单管理-添加订单")
    public Result addOrder(@Valid @RequestBody Order order) {
        List<Order> list = orderService.findByDingdanhao(order.getDingdanhao());
        if(null != list || !list.isEmpty()){
            return Result.result(666,"订单号已存在不能重复添加！",order);
        }
        Dict status = orderService.findByTypenameAndValue("dingdanzhuangtai","10");
        order.setStatus(status);
        order.setRukuguige(order.getPibuguige());
        order = orderService.save(order);
        return Result.ok("新增订单成功！", order);
    }

    @GetMapping("query_page")
    public Result findAll(Order order,Pageable pageable) {
        return Result.ok(orderService.findAll(order,pageable));
    }

    @GetMapping("deleteOrder")
    @Logger(msg = "撤销订单信息")
    @ApiOperation("订单管理-撤销订单")
    public Result deleteOrder(Long id) {
        orderService.deleteById(id);
        return Result.ok("查询成功！", id); //fixme 有可能是假删除
    }

    @PostMapping("updateOrder")
    @Logger(msg = "修改订单信息")
    @ApiOperation("订单管理-修改订单")
    public Result updateOrder(@RequestBody Order order) {
        List<Order> list = orderService.findByDingdanhao(order.getDingdanhao());
        if(null != list || !list.isEmpty()){
            return Result.result(666,"订单号已存在不能修改！",order);
        }
        orderService.update(order);
        return Result.ok("查询成功！", order);
    }

    @PostMapping("getOrder")
    @ApiOperation("订单管理-订单信息")
    public Result getOrder(Long id) {
        Order order = orderService.findByid(id);
        return Result.ok("查询成功！", order);
    }

    @GetMapping("getUser")
    @ApiOperation("获取在职人员")
    public Result getUser() {
        List<User> users =  userService.getByZaizhi(1);//查出所有的在职人员 //fixme 注意还有经理和营销员的区分
        return Result.ok("查询成功！", users);
    }
}
