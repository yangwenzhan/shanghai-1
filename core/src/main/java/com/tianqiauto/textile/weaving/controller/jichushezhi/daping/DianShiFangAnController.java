package com.tianqiauto.textile.weaving.controller.jichushezhi.daping;

import com.tianqiauto.textile.weaving.model.sys.TV_DianShiFangAn;
import com.tianqiauto.textile.weaving.repository.DianShiFangAnRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.DianShiFangAnService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DianShiFangAnController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/8 19:02
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/daping/dianshifangan")
@Api(description = "大屏管理通知公告")
public class DianShiFangAnController {

    @Autowired
    private DianShiFangAnService dianShiFangAnService;

    @Autowired
    private DianShiFangAnRepository dianShiFangAnRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有电视方案")
    public Result findAll(){
        return dianShiFangAnService.findAll();
    }

    @PostMapping("addDSFA")
    @ApiOperation(value = "添加电视方案")
    public Result addDSFA(@RequestBody TV_DianShiFangAn dianShiFangAn){
        dianShiFangAnRepository.save(dianShiFangAn);
        return Result.ok("新增成功",dianShiFangAn);
    }

    @PostMapping("delDSFA")
    @ApiOperation(value = "删除电视方案")
    public Result delDSFA(@RequestBody TV_DianShiFangAn dianShiFangAn){
        dianShiFangAnRepository.delete(dianShiFangAn);
        return Result.ok("删除成功",dianShiFangAn);
    }







}
