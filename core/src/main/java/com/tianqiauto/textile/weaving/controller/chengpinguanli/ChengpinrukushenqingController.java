package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinrukushenqingServer;
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
@RequestMapping("chengpinguanli/chengpinrukushenqing")
public class ChengpinrukushenqingController {

    @Autowired
    private ChengpinrukushenqingServer chengpinrukushenqingServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_RuKu_Shenqing chengpinRuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinrukushenqingServer.findAll(chengpinRuKuShenqing,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "成品入库申请新增")
    @ApiOperation("成品管理-成品入库申请新增")
    @ResponseBody
    public Result add(@RequestBody Chengpin_RuKu_Shenqing chengpinRuKuShenqing){
        chengpinRuKuShenqing = chengpinrukushenqingServer.save(chengpinRuKuShenqing);
        return Result.ok("添加成功！",chengpinRuKuShenqing);
    }

    @GetMapping("delete")
    @Logger(msg = "成品管理-根据id删除成品入库申请信息")
    @ApiOperation("成品管理-根据id删除成品入库申请信息")
    public Result delete(Long id) {
        chengpinrukushenqingServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "成品管理-修改成品入库申请信息")
    @ApiOperation("成品管理-修改成品入库申请信息")
    public Result update(@RequestBody Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
        chengpinrukushenqingServer.update(chengpinRuKuShenqing);
        return Result.ok("修改成功！", chengpinRuKuShenqing);
    }

}
