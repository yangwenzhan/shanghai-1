package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.YuanShaRuKuService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author bjw
 * @Date 2019/3/19 14:36
 */
@Controller
@RequestMapping("yuansharuku")
public class YuanShaRuKuController {


    @Autowired
    private YuanShaRuKuService yuanShaRuKuService;

    @PostMapping("addShenqing")
    @Logger(msg = "原纱入库申请添加")
    @ApiOperation("原纱入库-原纱入库申请添加")
    @ResponseBody
    public Result addShenqing(@Valid @RequestBody YuanSha_RuKu_Shenqing yuanShaRuKuShenqing) {
        yuanShaRuKuShenqing = yuanShaRuKuService.save(yuanShaRuKuShenqing);
        return Result.ok("新增申请成功！", yuanShaRuKuShenqing);
    }

    @PostMapping("updateShenqing")
    @Logger(msg = "原纱入库申请修改")
    @ApiOperation("原纱入库-原纱入库申请修改")
    @ResponseBody
    public Result updateShenqing(@Valid @RequestBody YuanSha_RuKu_Shenqing yuanShaRuKuShenqing) {
        int ret = yuanShaRuKuService.updateShenqing(yuanShaRuKuShenqing);
        return Result.ok("修改成功！", ret);
    }

    @PostMapping("findByIdSQ")
    @ApiOperation("原纱入库-原纱入库申请详情")
    @ResponseBody
    public Result updateShenqing(Long id) {
        YuanSha_RuKu_Shenqing yuanShaRuKu = yuanShaRuKuService.findByIdSQ(id);
        return Result.ok("查询成功！", yuanShaRuKu);
    }

    @GetMapping("deleteSQ")
    @Logger(msg = "撤销原纱入库申请信息")
    @ApiOperation("原纱入库-撤销申请")
    @ResponseBody
    public Result deleteSQ(Long id) {
        yuanShaRuKuService.deleteSQ(id);
        return Result.ok("撤销申请成功！", id);
    }


    //----------------------------------------------------------------不需要 申请，直接登记

    @PostMapping("addDengJi")
    @Logger(msg = "原纱入库登记添加")
    @ApiOperation("原纱入库-原纱入库登记添加")
    @ResponseBody
    public Result addDengJi(@Valid @RequestBody YuanSha_RuKu yuanShaRuKu) {
        yuanShaRuKu = yuanShaRuKuService.addDengJi(yuanShaRuKu);
        return Result.ok("新增登记成功！", yuanShaRuKu);
    }

    @PostMapping("updateDengji")
    @Logger(msg = "原纱入库登记修改")
    @ApiOperation("原纱入库-原纱入库登记修改")
    @ResponseBody
    public Result updateDengji(@Valid @RequestBody YuanSha_RuKu yuanShaRuKu) {
        int ret = yuanShaRuKuService.updateDengji(yuanShaRuKu);
        return Result.ok("修改成功！", ret);
    }

    @PostMapping("findByIdDJ")
    @ApiOperation("原纱入库-原纱入库登记详情")
    @ResponseBody
    public Result findByIdDJ(Long id) {
        YuanSha_RuKu yuanShaRuKu = yuanShaRuKuService.findByIdDJ(id);
        return Result.ok("查询成功！", yuanShaRuKu);
    }

    @GetMapping("deleteDJ")
    @Logger(msg = "撤销原纱入库登记信息")
    @ApiOperation("原纱入库-撤销登记")
    @ResponseBody
    public Result deleteDJ(Long id) {
        yuanShaRuKuService.deleteDJ(id);
        return Result.ok("撤销成功！", id);
    }

}
