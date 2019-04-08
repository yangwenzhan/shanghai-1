package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        return Result.ok(proc);
    }

}
