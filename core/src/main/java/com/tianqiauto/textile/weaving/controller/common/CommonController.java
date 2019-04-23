package com.tianqiauto.textile.weaving.controller.common;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import com.tianqiauto.textile.weaving.repository.GongXuRepository;
import com.tianqiauto.textile.weaving.service.common.CommonService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName CommonController
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/25 15:14
 * @Version 1.0
 **/
@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private GongXuRepository gongXuRepository;

    @Autowired
    private Dict_TypeRepository dict_typeRepository;

    @Autowired
    private CommonService commonService;

    @GetMapping("findAllGX")
    @ApiOperation(value = "查询所有工序")
    public Result findAllGX(){
        List<Gongxu> list = gongXuRepository.findAllGX();
        return Result.ok("查询成功!",list);
    }

    @GetMapping("findAllJX")
    @ApiOperation(value = "查询工序下机型")
    public Result findAllJX(Long gongxu){
        List<Gongxu> list = gongXuRepository.findAllByParentGongxu(gongxu);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("findAllCSLB")
    @ApiOperation(value = "根据工序机型查询参数类别")
    public Result findAllCSLB(String gongxu, String jixing){
        List list = commonService.findCSLB(gongxu, jixing);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("findAllDictVal")
    @ApiOperation(value = "根据数据字典类别查询数据字典值",notes = "比如：传入轮班的code，查出来所有轮班值")
    public Result findAllDictVal(String code){

        Dict_Type dict_type = dict_typeRepository.findByCode(code);
        return Result.ok("查询成功!",dict_type);
    }

    @GetMapping("findUserZu")
    @ApiOperation(value = "获取员工分组")
    public Result findUserZu(){
        List<Map<String,Object>> list = commonService.findUserZu();
        return Result.ok(list);
    }

    @GetMapping("findZhiJiJiXing")
    @ApiOperation(value = "查询织机机型")
    public Result findZhiJiJiXing(){
        List list = commonService.findZhiJiJiXing();
        return Result.ok("查询成功",list);
    }

    @GetMapping("DictFindAllByCodes")
    @ApiOperation(value = "根据数据字典类型的code查询出dict数据并封装成map类型返回，key=code，val=Set<dict> ",notes = "传入查询需要的多个code，codes是数组对象")
    public Result DictFindAllByCodes(String[] codes){
        Set<String> set = new HashSet<>(Arrays.asList(codes));
        Map<String,Set<Dict>> map = commonService.DictFindAllByCodes(set);
        return Result.ok("查询成功!",map);
    }

    @GetMapping("findUser")
    @ApiOperation(value = "查询员工信息")
    public Result findUser(String gxid, String lbid, String roleid){
        List<Map<String,Object>> list = commonService.findUser(gxid, lbid, roleid);
        return Result.ok("查询成功",list);
    }

    @GetMapping("findHeYueHao")
    @ApiOperation(value = "查询所有的合约号")
    public Result findHeYueHao(Long id){
        List<Map<String,Object>> list = commonService.findHeYueHao(id);
        return Result.ok("查询成功",list);
    }

    @GetMapping("findJiTaiHao")
    @ApiOperation(value = "传入工序机型名称，查询机台号，可传空")
    public Result findJiTaiHao(String gongxu,String jixing){
        List<Map<String,Object>> list = commonService.findJiTaiHao(gongxu, jixing);
        return Result.ok("查询成功",list);
    }

}
