package com.tianqiauto.textile.weaving.service.jichushezhi;


import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SheBeiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BaseService baseService;

    //根据工序机型查询
    public Result findAllSheBei(String gx_id, String jx_id){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(gx_id).addInVarchar(jx_id);
        ProcedureContext pro=baseService.callProcedure("pc_base_shebei", ppu.getList());
        return Result.ok(pro.getDatas());
    }


    public int updateJHTC(Integer jihuatingtai,Long id){
        String sql = "update base_shebei set jihuatingtai=? where id=?";
        return jdbcTemplate.update(sql,jihuatingtai,id);
    }

    public int updateInfo(Long id,String zhizaoshang,String ip,String port) {
        String sql = "update base_shebei set zhizaoshang=?,ip=?,port=? where id=?";
        return jdbcTemplate.update(sql,zhizaoshang,ip,port,id);
    }

}
