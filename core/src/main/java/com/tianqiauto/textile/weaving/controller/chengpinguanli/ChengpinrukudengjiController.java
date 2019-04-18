package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinrukudengjiServer;
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
@RequestMapping("chengpinguanli/chengpinrukudengji")
public class ChengpinrukudengjiController {

    @Autowired
    private ChengpinrukudengjiServer chengpinrukudengjiServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_RuKu chengpinRuKu, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinrukudengjiServer.findAll(chengpinRuKu,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "成品入库登记新增")
    @ApiOperation("成品管理-成品入库登记新增")
    @ResponseBody
    public Result add(@RequestBody Chengpin_RuKu chengpinRuKu){
        chengpinRuKu = chengpinrukudengjiServer.save(chengpinRuKu);
        return Result.ok("添加成功！",chengpinRuKu);
    }

    @GetMapping("delete")
    @Logger(msg = "成品管理-根据id删除成品登记信息")
    @ApiOperation("成品管理-根据id删除成品登记信息")
    public Result delete(Long id) {
        chengpinrukudengjiServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "成品管理-修改成品入库登记信息")
    @ApiOperation("成品管理-修改成品入库登记信息")
    public Result update(@RequestBody Chengpin_RuKu chengpinRuKu) {
        chengpinrukudengjiServer.update(chengpinRuKu);
        return Result.ok("修改成功！", chengpinRuKu);
    }

}
