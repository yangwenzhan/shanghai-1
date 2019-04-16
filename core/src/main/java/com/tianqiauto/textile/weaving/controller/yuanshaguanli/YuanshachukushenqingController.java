package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuRepository;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshachukushenqingServer;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuansharukushenqingServer;
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
@RequestMapping("yuanshaguanli/yuanshachukushenqing")
public class YuanshachukushenqingController {

    @Autowired
    private YuanshachukushenqingServer yuanshachukushenqingServer;

    @Autowired
    private YuanShaChuKuRepository yuanShaChuKuRepository;



    @GetMapping("query_page")
    public Result findAll(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuanshachukushenqingServer.findAll(yuanSha_chuKu_shenqing,pageable));
    }

    @PostMapping("add")
    @Logger(msg = "原纱出库申请添加申请")
    @ApiOperation("原纱管理-原纱出库申请添加")
    @ResponseBody
    public Result add(@RequestBody YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing){
        YuanSha_ChuKu yuanSha_chuKu = yuanSha_chuKu_shenqing.getYuanShaChuKu();
        yuanSha_chuKu.setChukuleixing(yuanSha_chuKu_shenqing.getChukuleixing());
        yuanSha_chuKu.setYuanSha(yuanSha_chuKu_shenqing.getYuanSha());
        yuanSha_chuKu = yuanShaChuKuRepository.save(yuanSha_chuKu);
        yuanSha_chuKu_shenqing.setYuanShaChuKu(yuanSha_chuKu);
        yuanSha_chuKu_shenqing = yuanshachukushenqingServer.save(yuanSha_chuKu_shenqing);
        yuanSha_chuKu.setYuanSha_chuKu_shenqing(yuanSha_chuKu_shenqing);
        yuanShaChuKuRepository.save(yuanSha_chuKu);
        return Result.ok("添加成功！",yuanSha_chuKu_shenqing);
    }

    @GetMapping("delete")
    @Logger(msg = "原纱出库申请-根据id删除信息")
    @ApiOperation("原纱出库申请-根据id删除信息")
    public Result delete(Long id) {
        yuanshachukushenqingServer.deleteById(id);
        return Result.ok("删除成功！", id);
    }

    @PostMapping("update")
    @Logger(msg = "原纱申请-修改原纱出库申请信息")
    @ApiOperation("原纱申请-修改原纱出库申请信息")
    public Result update(@RequestBody YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing) {
        YuanSha_ChuKu_Shenqing shenqing = yuanshachukushenqingServer.update(yuanSha_chuKu_shenqing);
        return Result.ok("修改成功！", shenqing);
    }



}
