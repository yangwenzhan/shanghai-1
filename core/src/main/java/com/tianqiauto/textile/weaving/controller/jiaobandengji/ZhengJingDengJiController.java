package com.tianqiauto.textile.weaving.controller.jiaobandengji;

import com.tianqiauto.textile.weaving.model.result.ResultShiftZhengJing;
import com.tianqiauto.textile.weaving.model.sys.Shift_Zhengjing;
import com.tianqiauto.textile.weaving.service.jiaobandengji.ZhengJingDengJiService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ZhengJingDengJiController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/24 8:59
 * @Version 1.0
 **/
@RestController
@RequestMapping("jiaoban/zhengjingdengji")
public class ZhengJingDengJiController {

    @Autowired
    private ZhengJingDengJiService zhengJingDengJiService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询整经交班产量")
    public Result findAll(Shift_Zhengjing shift_zhengjing, @PageableDefault(sort={"riqi","banci"},direction = Sort.Direction.ASC ) Pageable pageable){
        shift_zhengjing.setShifouwancheng(0);
        Page<Shift_Zhengjing> shift_zhengjings = zhengJingDengJiService.findAll(shift_zhengjing, pageable);
        return Result.ok("查询成功", ResultShiftZhengJing.convert(shift_zhengjings));
    }

    @PostMapping("save")
    @ApiOperation(value = "整经产量登记")
    public Result save(@RequestBody Shift_Zhengjing shift_zhengjing){
        shift_zhengjing.setShifouwancheng(0);
        //1.轴，2.桶
        if(shift_zhengjing.getFlag().equals(1)){
            shift_zhengjing.setTiaoshu(null);
        }
        Shift_Zhengjing saveIndb = zhengJingDengJiService.save(shift_zhengjing);
        return Result.ok("操作成功",saveIndb);
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除整经产量登记")
    public Result delete(@RequestBody Shift_Zhengjing shift_zhengjing){
        zhengJingDengJiService.delete(shift_zhengjing);
        return Result.ok("删除成功",shift_zhengjing);
    }

    @GetMapping("findHeYueHao")
    @ApiOperation(value = "查询未完成的订单对应的合约号")
    public Result findHeYueHao(){
        return Result.ok("查询成功",zhengJingDengJiService.findHeYueHao());
    }

}
