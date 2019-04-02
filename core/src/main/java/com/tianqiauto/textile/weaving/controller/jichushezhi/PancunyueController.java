package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import com.tianqiauto.textile.weaving.repository.PanCunYueRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.PanCunYueService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("findAll")
    @ApiOperation(value = "查询所有")
    public Result query_page(@RequestBody PanCunYue panCunYue, Pageable pageable){
        System.out.println(pageable.getSort());
        Page<PanCunYue> panCunYues = panCunYueService.findAll(panCunYue,pageable);
        return Result.ok("查询成功",panCunYues);
    }

    @PostMapping("addPanCunYue")
    @ApiOperation(value = "新增盘存月")
    public Result addPanCunYue(@RequestBody PanCunYue panCunYue){
        panCunYueRepository.save(panCunYue);
        return Result.ok("新增成功",panCunYue);
    }



}
