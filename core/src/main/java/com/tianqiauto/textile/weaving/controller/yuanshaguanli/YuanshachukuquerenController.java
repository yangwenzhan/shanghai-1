package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuRepository;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshachukuquerenServer;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshachukushenqingServer;
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
 * @Date 2019/4/11 17:44
 */
@RestController
@RequestMapping("yuanshaguanli/yuanshachukuqueren")
public class YuanshachukuquerenController {

   @Autowired
   private YuanshachukuquerenServer yuanshachukuquerenServer;



    @GetMapping("query_page")
    public Result findAll(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuanshachukuquerenServer.findAll(yuanSha_chuKu_shenqing,pageable));
    }

    @PostMapping("update")
    @Logger(msg = "原纱申请-修改原纱出库申请信息")
    @ApiOperation("原纱申请-修改原纱出库申请信息")
    public Result update(@RequestBody YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing) {
        YuanSha_ChuKu_Shenqing yuanShaChuKuShenqingDB = yuanshachukuquerenServer.findById(yuanSha_chuKu_shenqing.getId());
        Double kcl = yuanShaChuKuShenqingDB.getYuanSha().getKucunliang();
        Double zz = yuanSha_chuKu_shenqing.getYuanShaChuKu().getZongzhong();
        if (kcl < zz){
            return Result.result(666,"原纱库存不足！",yuanSha_chuKu_shenqing);
        }
        YuanSha_ChuKu_Shenqing shenqing = yuanshachukuquerenServer.update(yuanSha_chuKu_shenqing);
        return Result.ok("修改成功！", shenqing);
    }



}
