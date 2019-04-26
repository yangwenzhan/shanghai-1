package com.tianqiauto.textile.weaving.controller.jiaobandengji;

import com.tianqiauto.textile.weaving.model.result.ResultShiftChuanZong;
import com.tianqiauto.textile.weaving.model.sys.Shift_ChuanZong;
import com.tianqiauto.textile.weaving.service.jiaobandengji.ChuanZongDengJiService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ChuanZongController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 10:30
 * @Version 1.0
 **/
@RestController
@RequestMapping("jiaoban/chuanzongdengji")
public class ChuanZongController {

    @Autowired
    private ChuanZongDengJiService chuanZongDengJiService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询穿综交班产量")
    public Result findAll(Shift_ChuanZong shift_chuanZong, @PageableDefault(sort={"riqi","banci"},direction = Sort.Direction.ASC ) Pageable pageable){
        shift_chuanZong.setShifouwancheng(0);
        Page<Shift_ChuanZong> shift_chuanZongs = chuanZongDengJiService.findAll(shift_chuanZong, pageable);
        return Result.ok("查询成功", ResultShiftChuanZong.convert(shift_chuanZongs));
    }

    @PostMapping("save")
    @ApiOperation(value = "新增或修改穿综交班产量")
    public Result save(@RequestBody Shift_ChuanZong shift_chuanZong){
        shift_chuanZong.setShifouwancheng(0);
        Shift_ChuanZong saveIndb = chuanZongDengJiService.save(shift_chuanZong);
        return Result.ok("操作成功",saveIndb);
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除穿综交班产量登记")
    public Result delete(@RequestBody Shift_ChuanZong shift_chuanZong){
        chuanZongDengJiService.delete(shift_chuanZong);
        return Result.ok("删除成功",shift_chuanZong);
    }



}
