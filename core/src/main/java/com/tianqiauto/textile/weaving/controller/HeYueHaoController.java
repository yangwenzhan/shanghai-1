package com.tianqiauto.textile.weaving.controller;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.repository.HeYueHaoDao;
import com.tianqiauto.textile.weaving.repository.HeYueHaoRepository;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/11 16:17
 */
@RestController
@RequestMapping("heyuehao")
public class HeYueHaoController {

    @Autowired
    private HeYueHaoRepository heYueHaoRepository;

    @Autowired
    private HeYueHaoDao heYueHaoDao;

    @GetMapping("findByOrderid")
    @Logger(msg = "根据订单号查询合约号信息")
    @ApiOperation("合约号管理-查询合约号")
    public Result findByOrderid(Long orderId){
        List<Order> list = heYueHaoDao.findByOrderid(orderId);
        return Result.ok("查询成功！",list);
    }

    @PostMapping("addHeyuegao")
    @Logger(msg = "添加合约号信息")
    @ApiOperation("合约号管理-添加合约号信息")
    public Result addHeyuegao(@RequestBody Heyuehao heyuehao){
        heyuehao = heYueHaoRepository.save(heyuehao);
        return Result.ok("添加成功！",heyuehao);
    }

    @PostMapping("deleteById")
    @Logger(msg = "根据id删除合约号信息")
    @ApiOperation("合约号管理-根据id删除合约号信息")
    public Result deleteById(Long id){
        heYueHaoRepository.deleteById(id);
        return Result.ok("删除成功！","");
    }

}
