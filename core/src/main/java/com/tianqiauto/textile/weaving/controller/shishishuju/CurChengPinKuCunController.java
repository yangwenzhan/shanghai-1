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
 * @ClassName ChengPinKuCunController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:41
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/chengpinkucun")
@Api(description = "经轴实时状态")
public class CurChengPinKuCunController {

    @Autowired
    private CurDataService curDataService;

    @GetMapping("cur_chengpin_kucun")
    @ApiOperation(value = "成品实时库存")
    public Result cur_chengpin_kucun(String heyuehao){
        return curDataService.cur_chengpin_kucun(heyuehao);
    }

}
