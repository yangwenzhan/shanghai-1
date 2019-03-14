package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.repository.OrderRepository;
import com.tianqiauto.textile.weaving.service.OrderService;
import com.tianqiauto.textile.weaving.util.Page.PageModel;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/14 9:05
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("addOrder")
    @Logger(msg = "添加订单信息")
    @ApiOperation("订单管理-添加订单")
    public Result addOrder(@Valid @RequestBody Order order){
        //order.setStatus(); fixme 添加默认（已创建）
        //fixme  入库规格 = 幅宽/经纱品种/纬纱品种/经密/纬密
        order = orderRepository.save(order);
        return Result.ok("新增订单成功！",order);
    }

    @GetMapping("findAll")
    @Logger(msg = "查询订单信息")
    @ApiOperation("订单管理-查询订单")
    public Result findAll(@RequestBody PageModel<Order> order){
        return Result.ok("查询成功！",order); //fixme 调用存储过程：
    }

}
