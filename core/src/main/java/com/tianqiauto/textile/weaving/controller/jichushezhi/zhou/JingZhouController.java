package com.tianqiauto.textile.weaving.controller.jichushezhi.zhou;

import com.tianqiauto.textile.weaving.model.sys.Beam_JingZhou;
import com.tianqiauto.textile.weaving.repository.JingZhouRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName JingZhouController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:31
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushuju/zhou/jingzhou")
@Api(description = "经轴管理")
public class JingZhouController {

    @Autowired
    JingZhouRepository jingZhouRepository;

    @GetMapping("findAllJingZhou")
    @ApiOperation(value = "查询经轴信息")
    public Result findAllJingZhou(Pageable pageable){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.ASC, "zhouhao");
        Page<Beam_JingZhou> list = jingZhouRepository.findAll(pageRequest);
        return Result.ok("查询成功!",list);
    }

    @PostMapping("updateJingZhou")
    @ApiOperation(value = "修改经轴信息")
    public Result updateJingZhou(@RequestBody Beam_JingZhou jingZhou){
        String beizhu = StringUtils.isEmpty(jingZhou.getBeizhu())?null:jingZhou.getBeizhu();

        jingZhouRepository.updateJingZhou(jingZhou.getZhoukuan(), beizhu, jingZhou.getId());
        return Result.ok("修改成功",jingZhou);
    }

    @PostMapping("addJingZhou")
    @ApiOperation(value = "新增经轴")
    public Result addJingZhou(@RequestBody Beam_JingZhou jingZhou){
        boolean flag = jingZhouRepository.existsByZhouhao(jingZhou.getZhouhao());
        if(!flag){
            jingZhouRepository.save(jingZhou);
            return Result.ok("新增成功",jingZhou);
        }else{
            return Result.result(666,"经轴号已存在",jingZhou);
        }
    }

    @PostMapping("deleteJingZhou")
    @ApiOperation(value = "删除经轴")
    public Result deleteJingZhou(@RequestBody Beam_JingZhou jingZhou){
        jingZhouRepository.delete(jingZhou);
        return Result.ok("删除成功",jingZhou);
    }


}
