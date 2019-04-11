package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import com.tianqiauto.textile.weaving.repository.PanCunYueRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.PanCunYueService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PancunyueController
 * @Description 盘存月管理
 * @Author xingxiaoshuai
 * @Date 2019-03-16 09:15
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushezhi/pancunyue")
public class PancunyueController {

    @Autowired
    private PanCunYueService panCunYueService;

    @Autowired
    private PanCunYueRepository panCunYueRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有")
    public Result query_page(PanCunYue panCunYue, Pageable pageable){

        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add( new Sort.Order(Sort.Direction.ASC, "nian"));
        orders.add( new Sort.Order(Sort.Direction.ASC, "yue"));

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),
                new Sort(orders));
        System.out.println(pageRequest.getSort());
        Page<PanCunYue> panCunYues = panCunYueService.findAll(panCunYue,pageRequest);
        return Result.ok("查询成功",panCunYues);
    }

    @PostMapping("addPanCunYue")
    @ApiOperation(value = "新增盘存月")
    public Result addPanCunYue(@RequestBody PanCunYue panCunYue){
        boolean flag = panCunYueRepository.existsByNianAndYue(panCunYue.getNian(),panCunYue.getYue());
        if(!flag){
            panCunYueService.addPanCunYue(panCunYue);
            return Result.ok("新增成功",panCunYue);
        }else{
            return Result.result(666,"该月盘存已存在",panCunYue);
        }
    }

    @PostMapping("deletePanCunYue")
    @ApiOperation(value = "删除盘存月")
    public Result deletePanCunYue(@RequestBody PanCunYue panCunYue){
        panCunYueRepository.delete(panCunYue);
        return Result.ok("删除成功",panCunYue);
    }

    @PostMapping("updatePanCunYue")
    @ApiOperation(value = "修改盘存月")
    public Result updatePanCunYue(@RequestBody PanCunYue panCunYue){
        panCunYueService.updatePanCunYue(panCunYue);
        return Result.ok("修改成功",panCunYue);
    }





}
