package com.tianqiauto.textile.weaving.service;


import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureResult;
import com.tianqiauto.textile.weaving.util.procedure.core.ResultGenerator;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CurDataService {

    @Autowired
    BaseService baseService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //原纱实时库存
    public ProcedureResult cur_yuansha_kucun(String pihao, String pinming, String zhishu){
        pihao = StringUtils.isEmpty(pihao)?null:pihao;
        pinming = StringUtils.isEmpty(pinming)?null:pinming;
        zhishu = StringUtils.isEmpty(zhishu)?null:zhishu;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(pihao).addInVarchar(pinming).addInVarchar(zhishu);
        ProcedureContext pro=baseService.callProcedure("pc_cur_yuansha_kucun", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //成品实时库存
    public ProcedureResult cur_chengpin_kucun(String heyuehao){
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(heyuehao);
        ProcedureContext pro=baseService.callProcedure("pc_cur_chengpin_kucun", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //经轴实时数据查询
    public ProcedureResult cur_jingzhou(String zt_id,String heyuehao,String zhouhao){
        zt_id = StringUtils.isEmpty(zt_id)?null:zt_id;
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        zhouhao = StringUtils.isEmpty(zhouhao)?null:zhouhao;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(zt_id).addInVarchar(heyuehao).addInVarchar(zhouhao);
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //经轴实时数据--按合约号汇总
    public ProcedureResult cur_jingzhou_hyshz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou_heyuehaohuizong", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //经轴实时数据--按经轴状态汇总
    public ProcedureResult cur_jingzhou_zthz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou_zhuangtaihuizong", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }


    //织轴实时数据
    public ProcedureResult cur_zhizhou(String zt_id,String heyuehao,String zhouhao,String weizhi){
        zt_id = StringUtils.isEmpty(zt_id)?null:zt_id;
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        zhouhao = StringUtils.isEmpty(zhouhao)?null:zhouhao;
        weizhi = StringUtils.isEmpty(weizhi)?null:weizhi;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(zt_id).addInVarchar(heyuehao).addInVarchar(zhouhao).addInVarchar(weizhi);
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //织轴实时数据-按合约号汇总
    public ProcedureResult cur_zhizhou_hyshz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou_heyuehaohuizong", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }

    //织轴实时数据-按状态汇总
    public ProcedureResult cur_zhizhou_zthz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou_zhuangtaihuizong", ppu.getList());
        return ResultGenerator.genSuccessResult(pro);
    }




}
