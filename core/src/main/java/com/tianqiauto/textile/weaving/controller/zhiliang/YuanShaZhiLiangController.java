package com.tianqiauto.textile.weaving.controller.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.service.zhiliang.YuanShaZhiLiangService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName YuanSha
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/20 14:50
 * @Version 1.0
 **/
@RestController
@RequestMapping("zhiliang/yuanshazhiliang")
public class YuanShaZhiLiangController {

    @Autowired
    private YuanShaZhiLiangService yuanShaZhiLiangService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询原纱质量信息")
    public Result findAll(String pihao, String pinming,@PageableDefault( sort = { "createTime" }, direction = Sort.Direction.DESC) Pageable pageable){
        return Result.ok(yuanShaZhiLiangService.findAll(pihao, pinming, pageable));
    }

    @PostMapping("updYuanShaZhiLiang")
    @ApiOperation(value = "更新原纱质量")
    public Result updYuanShaZhiLiang(@RequestBody YuanSha yuanSha){
        yuanShaZhiLiangService.updYuanShaZhiLiang(yuanSha);
        return Result.ok("修改成功",yuanSha);
    }




}
