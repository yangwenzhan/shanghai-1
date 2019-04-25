package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinchukushenqingServer;
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
@RequestMapping("chengpinguanli/chengpinchukushenqing")
public class ChengpinchukushenqingController {

    @Autowired
    private ChengpinchukushenqingServer chengpinchukushenqingServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinchukushenqingServer.findAll(chengpinChuKuShenqing,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "成品出库申请新增")
    @ApiOperation("成品管理-成品出库申请新增")
    @ResponseBody
    public Result add(@RequestBody Chengpin_ChuKu_Shenqing chengpinChuKuShenqing){
        chengpinChuKuShenqing = chengpinchukushenqingServer.save(chengpinChuKuShenqing);
        return Result.ok("添加成功！",chengpinChuKuShenqing);
    }

    @GetMapping("delete")
    @Logger(msg = "成品管理-根据id删除成品出库申请信息")
    @ApiOperation("成品管理-根据id删除成品出库申请信息")
    public Result delete(Long id) {
        chengpinchukushenqingServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "成品管理-修改成品出库申请信息")
    @ApiOperation("成品管理-修改成品出库申请信息")
    public Result update(@RequestBody Chengpin_ChuKu_Shenqing chengpinChuKuShenqing) {
        chengpinchukushenqingServer.update(chengpinChuKuShenqing);
        return Result.ok("修改成功！", chengpinChuKuShenqing);
    }

}
