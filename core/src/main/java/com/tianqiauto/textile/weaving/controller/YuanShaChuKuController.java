package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.YuanShaChuKuService;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author bjw
 * @Date 2019/3/19 15:44
 */
@Controller
@RequestMapping("yuanshachuku")
public class YuanShaChuKuController {


    @Autowired
    private YuanShaChuKuService yuanShaChuKuService;

    @PostMapping("addShenqing")
    @Logger(msg = "原纱出库申请添加")
    @ApiOperation("原纱出库-原纱出库申请添加")
    @ResponseBody
    public Result addShenqing(@Valid @RequestBody YuanSha_ChuKu_Shenqing yuanShaChuKuShenqing) {
        yuanShaChuKuShenqing = yuanShaChuKuService.save(yuanShaChuKuShenqing);
        return Result.ok("新增申请成功！", yuanShaChuKuShenqing);
    }

    @PostMapping("updateShenqing")
    @Logger(msg = "原纱出库申请修改")
    @ApiOperation("原纱出库-原纱出库申请修改")
    @ResponseBody
    public Result updateShenqing(@Valid @RequestBody YuanSha_ChuKu_Shenqing yuanShaChuKuShenqing) {
        int ret = yuanShaChuKuService.updateShenqing(yuanShaChuKuShenqing);
        return Result.ok("修改成功！", ret);
    }

    @PostMapping("findByIdSQ")
    @ApiOperation("原纱出库-原纱出库申请详情")
    @ResponseBody
    public Result findByIdSQ(Long id) {
        YuanSha_ChuKu_Shenqing yuanShaRuKu = yuanShaChuKuService.findByIdSQ(id);
        return Result.ok("查询成功！", yuanShaRuKu);
    }

    @GetMapping("deleteSQ")
    @Logger(msg = "撤销原纱出库申请信息")
    @ApiOperation("原纱出库-撤销申请")
    @ResponseBody
    public Result deleteSQ(Long id) {
        yuanShaChuKuService.deleteSQ(id);
        return Result.ok("撤销申请成功！", id);
    }


    //----------------------------------------------------------------不需要 申请，直接登记

    @PostMapping("addDengJi")
    @Logger(msg = "原纱出库登记添加")
    @ApiOperation("原纱出库-原纱入库登记添加")
    @ResponseBody
    public Result addDengJi(@Valid @RequestBody YuanSha_ChuKu yuanShaChuKu) {
        yuanShaChuKu = yuanShaChuKuService.addDengJi(yuanShaChuKu);
        return Result.ok("新增登记成功！", yuanShaChuKu);
    }

    @PostMapping("updateDengji")
    @Logger(msg = "原纱出库登记修改")
    @ApiOperation("原纱出库-原纱出库登记修改")
    @ResponseBody
    public Result updateDengji(@Valid @RequestBody YuanSha_ChuKu yuanShaChuKu) {
        int ret = yuanShaChuKuService.updateDengji(yuanShaChuKu);
        return Result.ok("修改成功！", ret);
    }

    @PostMapping("findByIdDJ")
    @ApiOperation("原纱出库-原纱出库登记详情")
    @ResponseBody
    public Result findByIdDJ(Long id) {
        YuanSha_ChuKu yuanShaChuKu = yuanShaChuKuService.findByIdDJ(id);
        return Result.ok("查询成功！", yuanShaChuKu);
    }

    @GetMapping("deleteDJ")
    @Logger(msg = "撤销原纱出库登记信息")
    @ApiOperation("原纱出库-撤销登记")
    @ResponseBody
    public Result deleteDJ(Long id) {
        yuanShaChuKuService.deleteDJ(id);
        return Result.ok("撤销成功！", id);
    }


}
