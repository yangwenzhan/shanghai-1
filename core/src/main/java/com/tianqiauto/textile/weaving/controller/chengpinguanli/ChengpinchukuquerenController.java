package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinchukuquerenServer;
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
@RequestMapping("chengpinguanli/chengpinchukuqueren")
public class ChengpinchukuquerenController {

    @Autowired
    private ChengpinchukuquerenServer chengpinchukuquerenServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinchukuquerenServer.findAll(chengpinChuKuShenqing,pageable));
    }


    @PostMapping("update")
    @Logger(msg = "成品管理-成品出库申请信息确认")
    @ApiOperation("成品管理-成品出库申请信息确认")
    public Result update(@RequestBody Chengpin_ChuKu_Shenqing chengpinChuKuShenqing) {
        chengpinchukuquerenServer.update(chengpinChuKuShenqing);
        return Result.ok("确认成功！", chengpinChuKuShenqing);
    }

}
