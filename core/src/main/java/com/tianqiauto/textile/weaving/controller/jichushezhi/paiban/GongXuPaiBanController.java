package com.tianqiauto.textile.weaving.controller.jichushezhi.paiban;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi_Xiangqing_Gongxu;
import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_GongXu_Repository;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
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
 * @ClassName GongXuPaiBanController
 * @Description 排班管理-工序对应排班
 * @Author lrj
 * @Date 2019/3/21 10:08
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/paiban/gongxupaiban")
@Api(description = "排班管理-工序对应排班")
public class GongXuPaiBanController {

    @Autowired
    private YunZhuanFangShi_GongXu_Repository yunZhuanFangShi_gongXu_repository;

    @Autowired
    BaseService baseService;

    @GetMapping("findAllGXPB")
    @ApiOperation(value = "查询工序运转方式")
    public Result findAll(){
        Sort sort = new Sort(Sort.Direction.ASC, "gongxu_id");
        List<PB_YunZhuanFangShi_Xiangqing_Gongxu> list = yunZhuanFangShi_gongXu_repository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @GetMapping("updateGongXuYZFS")
    @ApiOperation(value = "修改工序运转方式")
    public Result updateGongXuYZFS(String xq_id,String gxid){
        yunZhuanFangShi_gongXu_repository.updateGongXuYZFS(xq_id,gxid);
        //调用存储过程，修改近十年的排班规律
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(gxid).addInVarchar(xq_id).addOut();
        ProcedureContext pro=baseService.callProcedure("pc_upd_bancirole", ppu.getList());
        String success = pro.getParams().get(2).getValue();
        System.out.println(success);
        if("1".equals(success)){
            return Result.ok("修改成功!",pro);
        }else{
            return Result.error("排班规则修改失败!",pro);
        }

    }


}
