package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.repository.HeYueHaoRepository;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinCurrentServer;
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

    @Autowired
    private ChengpinCurrentServer chengpinCurrentServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinchukuquerenServer.findAll(chengpinChuKuShenqing,pageable));
    }


    @Autowired
    private HeYueHaoRepository heYueHaoRepository;

    @PostMapping("update")
    @Logger(msg = "成品管理-成品出库申请信息确认")
    @ApiOperation("成品管理-成品出库申请信息确认")
    public Result update(@RequestBody Chengpin_ChuKu_Shenqing chengpinChuKuShenqing) {
        Chengpin_ChuKu chengpinchuku = chengpinChuKuShenqing.getChengpinchuku();
        Chengpin_Current chengpinCurrent = chengpinCurrentServer.findByHeyuehao(heYueHaoRepository.findById(chengpinchuku.getHeyuehao().getId()).get());
        if(chengpinCurrent.getChangdu() < chengpinchuku.getChangdu()){
            return Result.result(666,"合约号："+chengpinCurrent.getHeyuehao().getName()+"成品库存不足！",chengpinChuKuShenqing);
        }
        chengpinCurrent.setChangdu(chengpinCurrent.getChangdu()- chengpinchuku.getChangdu());
        chengpinchukuquerenServer.update(chengpinChuKuShenqing,chengpinCurrent);
        return Result.ok("确认成功！", chengpinChuKuShenqing);
    }

}
