package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinchukushenqingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author bjw
 * @Date 2019/3/30 11:04
 */
@RestController
@RequestMapping("chengpinguanli/chengpinchukushenqing")
public class ChengpinchukushenqingController {

    @Autowired
    private ChengpinchukushenqingServer chengpinchukushenqingServer;

//    @GetMapping("query_page")
//    public Result findAll(Chengpin_RuKu_Shenqing chengpinRuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
//        return Result.ok(chengpinrukushenqingServer.findAll(chengpinRuKuShenqing,pageable));
//    }
//
//    @PostMapping("add")
//    @Logger(msg = "成品入库申请新增")
//    @ApiOperation("成品管理-成品入库申请新增")
//    @ResponseBody
//    public Result add(@RequestBody Chengpin_RuKu_Shenqing chengpinRuKuShenqing){
//        chengpinRuKuShenqing = chengpinrukushenqingServer.save(chengpinRuKuShenqing);
//        return Result.ok("添加成功！",chengpinRuKuShenqing);
//    }
//
//    @GetMapping("delete")
//    @Logger(msg = "成品管理-根据id删除成品入库申请信息")
//    @ApiOperation("成品管理-根据id删除成品入库申请信息")
//    public Result delete(Long id) {
//        chengpinrukushenqingServer.deleteById(id);
//        return Result.ok("删除成功！", id);
//    }
//
//    @PostMapping("update")
//    @Logger(msg = "成品管理-修改成品入库申请信息")
//    @ApiOperation("成品管理-修改成品入库申请信息")
//    public Result update(@RequestBody Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
//        chengpinrukushenqingServer.update(chengpinRuKuShenqing);
//        return Result.ok("修改成功！", chengpinRuKuShenqing);
//    }

}
