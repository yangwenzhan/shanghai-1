package com.tianqiauto.textile.weaving.controller.jiaobandengji;

import com.tianqiauto.textile.weaving.model.result.ResultShiftJiangSha;
import com.tianqiauto.textile.weaving.model.sys.Shift_JiangSha;
import com.tianqiauto.textile.weaving.service.jiaobandengji.JiangShaDengJiService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName JiangShaDengJiController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 8:59
 * @Version 1.0
 **/
@RestController
@RequestMapping("jiaoban/jiangshadengji")
public class JiangShaDengJiController {

    @Autowired
    private JiangShaDengJiService jiangShaDengJiService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询浆纱交班产量")
    public Result findAll(Shift_JiangSha shift_jiangSha, @PageableDefault(sort={"riqi","banci"},direction = Sort.Direction.ASC ) Pageable pageable){
        shift_jiangSha.setShifouwancheng(0);
        Page<Shift_JiangSha> shift_jiangShas = jiangShaDengJiService.findAll(shift_jiangSha, pageable);
        return Result.ok("查询成功", ResultShiftJiangSha.convert(shift_jiangShas));
    }

    @PostMapping("save")
    @ApiOperation(value = "新增或修改浆纱交班产量")
    public Result save(@RequestBody Shift_JiangSha shift_jiangSha){
        shift_jiangSha.setShifouwancheng(0);
        Shift_JiangSha saveIndb = jiangShaDengJiService.save(shift_jiangSha);
        return Result.ok("操作成功",saveIndb);
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除浆纱交班产量登记")
    public Result delete(@RequestBody Shift_JiangSha shift_jiangSha){
        jiangShaDengJiService.delete(shift_jiangSha);
        return Result.ok("删除成功",shift_jiangSha);
    }


}
