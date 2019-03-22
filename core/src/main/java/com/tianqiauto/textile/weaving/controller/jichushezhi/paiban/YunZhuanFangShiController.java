package com.tianqiauto.textile.weaving.controller.jichushezhi.paiban;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi;
import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_Repository;
import com.tianqiauto.textile.weaving.service.PaiBanService;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName YunZhuanFangShiController
 * @Description 排班管理-运转方式
 * @Author lrj
 * @Date 2019/3/21 10:09
 * @Version 1.0
 **/
@RestController
@RequestMapping("jichushezhi/paiban/yunzhuanfangshi")
@Api(description = "排班管理-运转方式")
public class YunZhuanFangShiController {

    @Autowired
    private PaiBanService paiBanService;

    @Autowired
    private YunZhuanFangShi_Repository yunZhuanFangShi_repository;

    @GetMapping("findAllYZFS")
    @ApiOperation(value = "查询所有运转方式")
    public Result findAllYZFS(){
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<PB_YunZhuanFangShi> list = yunZhuanFangShi_repository.findAll(sort);
        return Result.ok("查询成功!",list);
    }

    @PostMapping("add_new_yzfs")
    @ApiOperation(value="新增运转方式",notes = "json对象和数组：conditions")
    public Result add_new_yzfs(String conditions){
        paiBanService.add_new_yzfs(conditions);
        return Result.ok("新增成功!",true);
    }

    @PostMapping("upd_yzfs_Info")
    @ApiOperation(value="修改运转方式的排班信息",notes = "js将每条数据封装成object，字符串传过来。参数：jsonArry对象  conditions")
    public Result upd_yzfs_Info(String conditions){
        JSONArray conditionArry = JSONArray.parseArray(conditions);
        List<Object[]> list = new ArrayList<>();
        if(conditionArry.size()>0){
            for(int i=0;i<conditionArry.size();i++){
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject job = conditionArry.getJSONObject(i);
                String[] arr = new String[3];
                arr[0] = job.getString("lunban_id");
//                arr[1] = job.getString("start_time");
//                arr[2] = job.getString("end_time");
                arr[1] = job.getString("yzfs_id");
                arr[2] = job.getString("pxh");
                list.add(arr);
            }
        }
        paiBanService.upd_yzfs_Info(list);
        return Result.ok("修改成功!",true);
    }

}
