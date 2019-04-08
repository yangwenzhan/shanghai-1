package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuansharukudengjiServer;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/30 11:04
 */
@RestController
@RequestMapping("yuanshaguanli/yuansharukudengji")
public class YuansharukudengjiController {

    @Autowired
    private YuansharukudengjiServer yuansharukudengjiServer;

    @GetMapping("query_page")
    public Result findAll(YuanSha_RuKu yuanSha_ruKu, Pageable pageable) {
        Dict status = new Dict();
        status.setValue("20");//查询申请单中状态已确认的。
        YuanSha_RuKu_Shenqing ysrk = new YuanSha_RuKu_Shenqing();
        ysrk.setStatus(status);
        yuanSha_ruKu.setYuanSha_ruKu_shenqing(ysrk);
        return Result.ok(yuansharukudengjiServer.findAll(yuanSha_ruKu,pageable));
    }

    @GetMapping("create_pihao")
    @ApiOperation("原纱入库管理-生成新批号")
    @ResponseBody
    public Result create_pihao(){
        Object ret = yuansharukudengjiServer.create_pihao();
        return Result.ok("成功！",ret);
    }

    @PostMapping("add")
    @Logger(msg = "原纱入库登记新增")
    @ApiOperation("原纱管理-原纱购入登记新增")
    @ResponseBody
    public Result add(@RequestBody YuanSha_RuKu yuanSha_ruKu){
        yuanSha_ruKu = yuansharukudengjiServer.save(yuanSha_ruKu);
        return Result.ok("添加成功！",yuanSha_ruKu);
    }

    @GetMapping("delete")
    @Logger(msg = "原纱购入登记-根据id删除登记信息")
    @ApiOperation("原纱购入登记-根据id删除登记信息")
    public Result delete(Long id) {
        yuansharukudengjiServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "原纱购入登记-修改原纱入库登记信息")
    @ApiOperation("原纱购入登记-修改原纱入库登记信息")
    public Result update(@RequestBody YuanSha_RuKu yuanSha_ruKu) {
        yuansharukudengjiServer.update(yuanSha_ruKu);
        return Result.ok("修改成功！", yuanSha_ruKu);
    }

}
