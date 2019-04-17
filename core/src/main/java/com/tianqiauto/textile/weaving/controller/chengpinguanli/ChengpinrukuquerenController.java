package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinrukuquerenServer;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinrukushenqingServer;
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
@RequestMapping("chengpinguanli/chengpinrukuqueren")
public class ChengpinrukuquerenController {

    @Autowired
    private ChengpinrukuquerenServer chengpinrukuquerenServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_RuKu_Shenqing chengpinRuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinrukuquerenServer.findAll(chengpinRuKuShenqing,pageable));
    }

    @PostMapping("update")
    @Logger(msg = "成品管理-登记成品入库申请信息")
    @ApiOperation("成品管理-登记成品入库申请信息")
    public Result update(@RequestBody Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
        chengpinrukuquerenServer.update(chengpinRuKuShenqing);
        return Result.ok("登记成功！", chengpinRuKuShenqing);
    }

}
