package com.tianqiauto.textile.weaving.controller.shishishuju;

import com.tianqiauto.textile.weaving.service.shishishuju.CurDataService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName YuanShaKuCunController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/yuanshakucun")
@Api(description = "经轴实时状态")
public class CurYuanShaKuCunController {

    @Autowired
    private CurDataService curDataService;

    @GetMapping("cur_yuansha_kucun")
    @ApiOperation(value = "原纱实时库存")
    public Result cur_yuansha_kucun(String pihao, String pinming, String zhishu){
        return curDataService.cur_yuansha_kucun(pihao, pinming, zhishu);
    }

}
