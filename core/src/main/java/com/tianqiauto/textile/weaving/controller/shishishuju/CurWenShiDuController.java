package com.tianqiauto.textile.weaving.controller.shishishuju;

import com.tianqiauto.textile.weaving.model.sys.History_WenShiDu;
import com.tianqiauto.textile.weaving.repository.HistoryWenShiDuRepository;
import com.tianqiauto.textile.weaving.service.shishishuju.CurDataService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CurWenShiDuController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/12 10:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("shishishuju/cur_wenshidu")
@Api(description = "经轴实时状态")
public class CurWenShiDuController {

    @Autowired
    private HistoryWenShiDuRepository historyWenShiDuRepository;

    @Autowired
    private CurDataService curDataService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询温湿度实时数据")
    public Result findAll(){
        List<Map<String,Object>> list = curDataService.cur_wenshidu_findAll();
        return Result.ok("查询成功",list);
    }

    @GetMapping("findHistory")
    @ApiOperation(value = "查询温湿度历史数据")
    public Result findHistory(Long id){
        List<History_WenShiDu> list = historyWenShiDuRepository.findAllHistory(id);
        return Result.ok("查询成功",list);
    }



}
