package com.tianqiauto.textile.weaving.controller.jichushezhi.paiban;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi_Xiangqing_Gongxu;
import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_GongXu_Repository;
import com.tianqiauto.textile.weaving.service.PaiBanService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GongXuPaiBanController
 * @Description 排班管理-工序对应排班
 * @Author lrj
 * @Date 2019/3/21 10:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/paiban/gongxupaiban")
@Api(description = "排班管理-工序对应排班")
public class GongXuPaiBanController {

    @Autowired
    private PaiBanService paiBanService;

    @Autowired
    private YunZhuanFangShi_GongXu_Repository yunZhuanFangShi_gongXu_repository;

    @GetMapping("findAllGXPB")
    @ApiOperation(value = "查询工序运转方式")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "gongxu_id");
        List<PB_YunZhuanFangShi_Xiangqing_Gongxu> list = yunZhuanFangShi_gongXu_repository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @PostMapping("updateGongXuYZFS")
    @ApiOperation(value = "修改工序运转方式")
    public Result updateGongXuYZFS(@RequestBody PB_YunZhuanFangShi_Xiangqing_Gongxu gxyz){
        yunZhuanFangShi_gongXu_repository.updateGongXuYZFS(gxyz.getPb_yunZhuanFangShi_xiangqing().getId(),
                gxyz.getSort(),
                gxyz.getGongxu().getId());
        return Result.ok("修改成功!",gxyz);
    }


}
