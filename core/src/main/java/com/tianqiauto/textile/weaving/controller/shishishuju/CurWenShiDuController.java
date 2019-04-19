package com.tianqiauto.textile.weaving.controller.shishishuju;

import com.tianqiauto.textile.weaving.model.sys.History_WenShiDu;
import com.tianqiauto.textile.weaving.model.sys.WenShiDu;
import com.tianqiauto.textile.weaving.repository.HistoryWenShiDuRepository;
import com.tianqiauto.textile.weaving.repository.WenShiDuRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private WenShiDuRepository wenShiDuRepository;

    @Autowired
    private HistoryWenShiDuRepository historyWenShiDuRepository;

    @GetMapping("findAll")
    @ApiOperation(value = "查询温湿度实时数据")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<WenShiDu> list = wenShiDuRepository.findAll(sort);
        return Result.ok("查询成功",list);
    }

    @GetMapping("findHistory")
    @ApiOperation(value = "查询温湿度历史数据")
    public Result findHistory(Long id){
        List<History_WenShiDu> list = historyWenShiDuRepository.findAllHistory(id);
        return Result.ok("查询成功",list);
    }



}
