package com.tianqiauto.textile.weaving.controller.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.JiangSha_ZhiLiang;
import com.tianqiauto.textile.weaving.service.zhiliang.JiangShaZhiLiangService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName JiangShaZhiLiangController
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/22 9:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("zhiliang/jiangshazhiliang")
public class JiangShaZhiLiangController {

    @Autowired
    private JiangShaZhiLiangService jiangShaZhiLiangService;


    @GetMapping("findAll")
    @ApiOperation(value = "查询浆纱质量")
    public Result findAll(String heyuehao,@PageableDefault(sort = {"heyuehao","ganghao"},direction = Sort.Direction.DESC) Pageable pageable){
        Page<JiangSha_ZhiLiang> jiangSha_zhiLiangs = jiangShaZhiLiangService.findAll(heyuehao,pageable);
        return Result.ok("查询成功",jiangSha_zhiLiangs);
    }

    @PostMapping("updJiangShaZhiLiang")
    @ApiOperation(value = "修改浆纱质量")
    public Result updJiangShaZhiLiang(@RequestBody JiangSha_ZhiLiang jiangSha_zhiLiang){
        jiangShaZhiLiangService.updJiangShaZhiLiang(jiangSha_zhiLiang);
        return Result.ok("操作成功",jiangSha_zhiLiang);
    }

    @GetMapping("findGangHao")
    @ApiOperation(value = "查询未录入浆纱质量的缸号")
    public Result findGangHao(String heyuehao_id){
        return Result.ok("查询成功!",jiangShaZhiLiangService.findGangHao(heyuehao_id));
    }

}
