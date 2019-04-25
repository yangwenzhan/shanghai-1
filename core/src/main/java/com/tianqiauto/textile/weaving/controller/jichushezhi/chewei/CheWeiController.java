package com.tianqiauto.textile.weaving.controller.jichushezhi.chewei;

import com.tianqiauto.textile.weaving.model.sys.CheWei;
import com.tianqiauto.textile.weaving.model.sys.CheWeiCurrent;
import com.tianqiauto.textile.weaving.service.jichushezhi.CheWeiService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CheWeiController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/19 8:46
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/chewei")
public class CheWeiController {

    @Autowired
    private CheWeiService cheWeiService;


    @GetMapping("findCheWei")
    @ApiOperation(value = "查询固定车位信息")
    public Result findCheWei(String ssgx,String sslb,String user,String jtgx,String jtjx){
        return cheWeiService.findCheWei(ssgx, sslb, user, jtgx, jtjx);
    }

    @PostMapping("updCheWei")
    @ApiOperation(value = "修改固定车位")
    public Result updCheWei(@RequestBody List<CheWei> cheWeis){
        cheWeiService.updCheWei(cheWeis);
        return Result.ok("修改成功",cheWeis);
    }

    @GetMapping("findCheWei_Current")
    @ApiOperation(value = "查询临时车位信息")
    public Result findCheWei_Current(String ssgx,String sslb,String user,String jtgx,String jtjx){
        return cheWeiService.findCheWei_Current(ssgx, sslb, user, jtgx, jtjx);
    }

    @PostMapping("updCheWei_Current")
    @ApiOperation(value = "修改临时车位")
    public Result updCheWei_Current(@RequestBody List<CheWeiCurrent> cheWeiCurrents){
        cheWeiService.updCheWei_Current(cheWeiCurrents);
        return Result.ok("修改成功",cheWeiCurrents);
    }



}
