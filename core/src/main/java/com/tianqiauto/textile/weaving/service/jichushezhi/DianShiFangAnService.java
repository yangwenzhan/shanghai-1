package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DianShiFangAnService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/8 19:03
 * @Version 1.0
 **/
@Service
public class DianShiFangAnService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BaseService baseService;

    public Result findAll(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_tv_plan");
        return Result.ok(proc.getDatas());
    }

    @Transactional
    public void updDSFA(String id,String[] ymids){
        String sql1 = "delete from sys_tv_ym_plan where dianshifangan_id=?";
        String sql2 = "insert into sys_tv_ym_plan(zhanshiyemian_id,dianshifangan_id) values(?,?)";
        List<Object[]> list = new ArrayList<>();
        for(int i=0;i<ymids.length;i++){
            String[] arr = new String[2];
            arr[0] = ymids[i];
            arr[1] = id;
            list.add(arr);
        }
        jdbcTemplate.update(sql1,id);
        jdbcTemplate.batchUpdate(sql2,list);
    }

    @Transactional
    public void delDSFA(String id){
        String sql1="delete from sys_tv_dianshifangan where id=?";
        String sql2="delete from sys_tv_ym_plan where dianshifangan_id=?";
        jdbcTemplate.update(sql1,id);
        jdbcTemplate.update(sql2,id);
    }



}
