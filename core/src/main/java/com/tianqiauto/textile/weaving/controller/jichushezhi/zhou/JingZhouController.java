package com.tianqiauto.textile.weaving.controller.jichushezhi.zhou;

import com.tianqiauto.textile.weaving.model.sys.Beam_JingZhou;
import com.tianqiauto.textile.weaving.repository.JingZhouRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Result findAllJingZhou(){
        Sort sort = new Sort(Sort.Direction.ASC, "zhouhao");
        List<Beam_JingZhou> list = jingZhouRepository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("updateJingZhou")
    @ApiOperation(value = "修改经轴信息")
    public Result updateJingZhou(@RequestBody Beam_JingZhou jingZhou){
        String beizhu = StringUtils.isEmpty(jingZhou.getBeizhu())?null:jingZhou.getBeizhu();

        jingZhouRepository.updateJingZhou(jingZhou.getZhoukuan(), beizhu, jingZhou.getId());
        return Result.ok("修改成功",jingZhou);
    }

}
