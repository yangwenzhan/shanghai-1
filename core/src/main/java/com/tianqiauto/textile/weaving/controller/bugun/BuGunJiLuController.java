package com.tianqiauto.textile.weaving.controller.bugun;

import com.tianqiauto.textile.weaving.model.sys.BuGun;
import com.tianqiauto.textile.weaving.service.bugun.BuGunJiLuService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BuGunJiLuController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/27 15:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("bugun/bugunjilu")
public class BuGunJiLuController {

    @Autowired
    private BuGunJiLuService buGunJiLuService;


    @GetMapping("findAll")
    @ApiOperation(value = "查询布辊产出记录")
    public Result findAll(BuGun buGun, @PageableDefault(sort={"riqi","banci","jitaihao"},direction = Sort.Direction.ASC ) Pageable pageable){
        Page<BuGun> buGuns = buGunJiLuService.findAll(buGun, pageable);
        return Result.ok("查询成功",buGuns);
    }





}
