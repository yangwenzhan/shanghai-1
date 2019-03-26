package com.tianqiauto.textile.weaving.controller.jichushezhi.zhou;

import com.tianqiauto.textile.weaving.model.sys.Beam_ZhiZhou;
import com.tianqiauto.textile.weaving.repository.ZhiZhouRepository;
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
 * @ClassName ZhiZhouController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/21 10:31
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushuju/zhou/zhizhou")
@Api(description = "经轴管理")
public class ZhiZhouController {

    @Autowired
    ZhiZhouRepository zhiZhouRepository;

    @GetMapping("findAllZhiZhou")
    @ApiOperation(value = "查询织轴信息")
    public Result findAllZhiZhou(){
        Sort sort = new Sort(Sort.Direction.ASC, "zhouhao");
        List<Beam_ZhiZhou> list = zhiZhouRepository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("updateZhiZhou")
    @ApiOperation(value = "修改织轴信息")
    public Result updateZhiZhou(@RequestBody Beam_ZhiZhou zhiZhou){
        String beizhu = StringUtils.isEmpty(zhiZhou.getBeizhu())?null:zhiZhou.getBeizhu();

        zhiZhouRepository.updateZhiZhou(zhiZhou.getJixing().getId(),zhiZhou.getZhoukuan(),beizhu,zhiZhou.getId());
        return Result.ok("修改成功",zhiZhou);
    }

}
