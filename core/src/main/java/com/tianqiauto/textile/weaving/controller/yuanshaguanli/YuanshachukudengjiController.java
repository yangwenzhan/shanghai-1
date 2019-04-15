package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshachukudengjiServer;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @Author bjw
 * @Date 2019/3/30 11:04
 */
@RestController
@RequestMapping("yuanshaguanli/yuanshachukudengji")
public class YuanshachukudengjiController {

    @Autowired
    private YuanshachukudengjiServer yuanshachukudengjiServer;

    @GetMapping("query_page")
    public Result findAll(YuanSha_ChuKu yuanSha_chuKu, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuanshachukudengjiServer.findAll(yuanSha_chuKu,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "原纱出库登记新增")
    @ApiOperation("原纱管理-原纱出库登记新增")
    @ResponseBody
    public Result add(@RequestBody YuanSha_ChuKu yuanSha_chuKu){
        yuanSha_chuKu = yuanshachukudengjiServer.save(yuanSha_chuKu);
        return Result.ok("添加成功！",yuanSha_chuKu);
    }

    @GetMapping("delete")
    @Logger(msg = "原纱出库登记-根据id删除登记信息")
    @ApiOperation("原纱出库登记-根据id删除登记信息")
    public Result delete(Long id) {
        yuanshachukudengjiServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "原纱购入登记-修改原纱入库登记信息")
    @ApiOperation("原纱购入登记-修改原纱入库登记信息")
    public Result update(@RequestBody YuanSha_ChuKu yuanSha_chuKu) {
        yuanshachukudengjiServer.update(yuanSha_chuKu);
        return Result.ok("修改成功！", yuanSha_chuKu);
    }

}
