package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jishuchuju/pinzhong")
@Api(description = "品种管理")
public class PinZhongController {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有原纱品种信息")
    public Result findAll(YuanSha yuanSha){
        List<YuanSha> list = yuanShaRepository.findAllByPihaoAndPinmingAndZhishu(yuanSha.getPihao(),yuanSha.getPinming(),yuanSha.getZhishu());
        return Result.ok("查询成功!",list);
    }

    @GetMapping("updPinZhong")
    @ApiOperation(value = "修改原纱品种信息")
    public Result updPinZhong(@RequestBody YuanSha yuanSha){

        yuanShaRepository.updPinZhong(yuanSha.getZhishu(),
                yuanSha.getGongyingshang().getId(),
                yuanSha.getSebie(),
                yuanSha.getSehao(),
                yuanSha.getBaozhuangxingshi().getId(),
                yuanSha.getBeizhu(),
                yuanSha.getId());

        return Result.ok("修改成功!",yuanSha);
    }

    @GetMapping(value = "savePinZhong")
    @ApiOperation(value = "新增原纱品种")
    public Result savePinZhong(@RequestBody YuanSha yuanSha){
        YuanSha ys = yuanShaRepository.save(yuanSha);
        return Result.ok("新增成功!",ys);
    }


}
