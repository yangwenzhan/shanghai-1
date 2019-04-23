package com.tianqiauto.textile.weaving.controller.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.ChengPin_ZhiLiang;
import com.tianqiauto.textile.weaving.service.zhiliang.ChengPinZhiLiangService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ChengPinController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/22 15:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("zhiliang/chengpinzhiliang")
public class ChengPinController {

    @Autowired
    private ChengPinZhiLiangService chengPinZhiLiangService;

    @GetMapping("getHeYueHao")
    @ApiOperation(value = "查询在日期范围内成品已入库的合约号")
    public Result getHeYueHao(String ksrq, String jsrq){
        List<Map<String,Object>> list = chengPinZhiLiangService.getHeYueHao(ksrq, jsrq);
        return Result.ok("查询成功",list);
    }

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有的成品质量")
    public Result findAll(ChengPin_ZhiLiang chengPin_zhiLiang,@PageableDefault(sort={"riqi","heyuehao"},direction = Sort.Direction.ASC ) Pageable pageable){
        Page<ChengPin_ZhiLiang> chengPin_zhiLiangs = chengPinZhiLiangService.findAll(chengPin_zhiLiang, pageable);
        return Result.ok("查询成功",chengPin_zhiLiangs);
    }

    @GetMapping("findRuKu")
    @ApiOperation(value = "批量新增：查询当日入库的成品信息")
    public Result findRuKu(String riqi){
        List<Map<String,Object>> list = chengPinZhiLiangService.findRuKu(riqi);
        return Result.ok("查询成功",list);
    }

    @GetMapping("findJiTaiHao")
    @ApiOperation(value = "批量新增：根据日期班次合约号获取对应的机台")
    public Result findJiTaiHao(String riqi,String banci_id,String hyh_id){
        List<Map<String,Object>> list = chengPinZhiLiangService.findJiTaiHao(riqi, banci_id, hyh_id);
        return Result.ok("查询成功",list);
    }

    @PostMapping("batchSave")
    @ApiOperation(value = "批量新增")
    public Result batchSave(@RequestBody Iterable<ChengPin_ZhiLiang> chengPin_zhiLiangs){
        chengPinZhiLiangService.batchSave(chengPin_zhiLiangs);
        return Result.ok("新增成功");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除成品质量")
    public Result delete(@RequestBody ChengPin_ZhiLiang chengPin_zhiLiang){
        chengPinZhiLiangService.delete(chengPin_zhiLiang);
        return Result.ok("删除成功",chengPin_zhiLiang);
    }

    @PostMapping("save")
    @ApiOperation(value = "单个新增或修改")
    public Result save(@RequestBody ChengPin_ZhiLiang chengPin_zhiLiang){
        chengPinZhiLiangService.save(chengPin_zhiLiang);
        return Result.ok("操作成功",chengPin_zhiLiang);
    }


}
