package com.tianqiauto.textile.weaving.controller.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.QiJi_ZhiLiang;
import com.tianqiauto.textile.weaving.service.zhiliang.QiJiZhiLiangService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName QiJiController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/23 16:04
 * @Version 1.0
 **/
@RestController
@RequestMapping("zhiliang/qijizhiliang")
public class QiJiController {

    @Autowired
    private QiJiZhiLiangService qiJiZhiLiangService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有的起机质量")
    public Result findAll(QiJi_ZhiLiang qiJi_zhiLiang, @PageableDefault(sort={"riqi","heyuehao"},direction = Sort.Direction.ASC ) Pageable pageable){
        Page<QiJi_ZhiLiang> qiJi_zhiLiangs = qiJiZhiLiangService.findAll(qiJi_zhiLiang, pageable);
        return Result.ok("查询成功",qiJi_zhiLiangs);
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除起机质量")
    public Result delete(@RequestBody QiJi_ZhiLiang qiJi_zhiLiang){
        qiJiZhiLiangService.delete(qiJi_zhiLiang);
        return Result.ok("删除成功",qiJi_zhiLiang);
    }

    @PostMapping("save")
    @ApiOperation(value = "单个新增或修改")
    public Result save(@RequestBody QiJi_ZhiLiang qiJi_zhiLiang){
        qiJiZhiLiangService.save(qiJi_zhiLiang);
        return Result.ok("操作成功",qiJi_zhiLiang);
    }

    @GetMapping("findHeYueHao")
    @ApiOperation(value = "根据日期班次机台号从布机归档表中查询对应的合约号")
    public Result findHeYueHao(String riqi,Long banci_id,Long jitaihao){
        return Result.ok("查询成功",qiJiZhiLiangService.findHeYueHao(riqi, banci_id, jitaihao));
    }

}
