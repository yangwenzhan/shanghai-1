package com.tianqiauto.textile.weaving.controller.jichushezhi.shebei;

import com.tianqiauto.textile.weaving.service.jichushezhi.SheBeiService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备管理
 */

@RestController
@RequestMapping("jichushuju/shebei/shebei")
@Api(description = "设备管理")
public class SheBeiController {

    @Autowired
    SheBeiService sheBeiService;

    @GetMapping("findAllSheBei")
    @ApiOperation(value = "查询所有设备信息",notes = "工序id，机型id")
    public Result findAllSheBei(String gongxu, String jixing){
        return sheBeiService.findAllSheBei(gongxu, jixing);
    }

    @GetMapping("updateJHTC")
    @ApiOperation(value = "修改计划停台")
    public Result updateJHTC(Integer jihuatingtai, Long id){
        int num = sheBeiService.updateJHTC(jihuatingtai,id);
        return Result.ok("修改成功!",num);
    }

    @GetMapping("updateInfo")
    @ApiOperation(value = "修改设备其他信息")
    public Result updateInfo(Long id, String zhizaoshang, String ip, String port){
        int num = sheBeiService.updateInfo(id, zhizaoshang, ip, port);
        return Result.ok("修改成功!",num);
    }




}
