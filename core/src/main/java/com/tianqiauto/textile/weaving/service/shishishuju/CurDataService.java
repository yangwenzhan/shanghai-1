package com.tianqiauto.textile.weaving.service.shishishuju;


import com.tianqiauto.textile.weaving.model.sys.History_Param;
import com.tianqiauto.textile.weaving.repository.HistoryParamRepository;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class CurDataService {

    @Autowired
    BaseService baseService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HistoryParamRepository historyParamRepository;

    //原纱实时库存
    public Result cur_yuansha_kucun(String pihao, String pinming, String zhishu){
        pihao = StringUtils.isEmpty(pihao)?null:pihao;
        pinming = StringUtils.isEmpty(pinming)?null:pinming;
        zhishu = StringUtils.isEmpty(zhishu)?null:zhishu;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(pihao).addInVarchar(pinming).addInVarchar(zhishu);
        ProcedureContext pro=baseService.callProcedure("pc_cur_yuansha_kucun", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //成品实时库存
    public Result cur_chengpin_kucun(String heyuehao){
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(heyuehao);
        ProcedureContext pro=baseService.callProcedure("pc_cur_chengpin_kucun", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //经轴实时数据查询
    public Result cur_jingzhou(String zt_id,String heyuehao,String zhouhao){
        zt_id = StringUtils.isEmpty(zt_id)?null:zt_id;
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        zhouhao = StringUtils.isEmpty(zhouhao)?null:zhouhao;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(zt_id).addInVarchar(heyuehao).addInVarchar(zhouhao);
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //经轴实时数据--按合约号汇总
    public Result cur_jingzhou_hyshz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou_heyuehaohuizong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //经轴实时数据--按经轴状态汇总
    public Result cur_jingzhou_zthz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_jingzhou_zhuangtaihuizong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //经轴实时数据--查询经轴实时数据对应的合约号
    public Result cur_jingzhou_hyh(){
        String sql = "select distinct a.id,a.name from sys_heyuehao a join sys_beam_jingzhou_current b on a.id=b.heyuehao_id";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return Result.ok("查询成功!",list);
    }

    //织轴实时数据
    public Result cur_zhizhou(String zt_id,String heyuehao,String zhouhao,String weizhi){
        zt_id = StringUtils.isEmpty(zt_id)?null:zt_id;
        heyuehao = StringUtils.isEmpty(heyuehao)?null:heyuehao;
        zhouhao = StringUtils.isEmpty(zhouhao)?null:zhouhao;
        weizhi = StringUtils.isEmpty(weizhi)?null:weizhi;
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(zt_id).addInVarchar(heyuehao).addInVarchar(zhouhao).addInVarchar(weizhi);
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //织轴实时数据-按合约号汇总
    public Result cur_zhizhou_hyshz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou_heyuehaohuizong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //织轴实时数据-按状态汇总
    public Result cur_zhizhou_zthz(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_cur_zhizhou_zhuangtaihuizong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //织轴实时数据--查询织轴实时数据对应的合约号
    public Result cur_zhizhou_hyh(){
        String sql = "select distinct a.id,a.name from sys_heyuehao a join sys_beam_zhizhou_current b on a.id=b.heyuehao_id";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return Result.ok("查询成功!",list);
    }

    //布机实时数据
    public Result cur_buji(String jx_id,String hyh_id,String yxzt_id, String online){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInInteger(jx_id).addInInteger(hyh_id).addInInteger(yxzt_id).addInInteger(online);
        ProcedureContext pro=baseService.callProcedure("pc_cur_buji", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //查询历史曲线按钮
    public Result findHisDataBtn(String jt_id){
        String sql = "select id,name,xuhao from sys_param where zhanshi_flag=1 and cunchu_flag=1 and leibie_id in (select id from sys_param_leibie where jixing_id =(select gongxu_id from base_shebei where id=?)) order by xuhao";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,jt_id);
        return Result.ok("查询成功!",list);
    }

    //查询机台详细参数
    public Result queryXxcsByJtid(String jt_id){
        String sql = "select a.value,a.jitai_id,a.param_id,b.danwei,b.name,b.xuhao,b.leibie_id,b.leibie_name,b.lbxh from sys_current as a join " +
                "( " +
                "select a.*,b.name as leibie_name,b.xuhao as lbxh from sys_param a join sys_param_leibie b on a.leibie_id=b.id  " +
                "where a.leibie_id in (  " +
                " select id from sys_param_leibie  " +
                " where jixing_id = ( " +
                " select gongxu_id from base_shebei where id=? " +
                " )  " +
                ")  " +
                "and zhanshi_flag=1 and baojing_flag=0  " +
                ") as b on a.param_id=b.id " +
                "where a.jitai_id=? " +
                "order by xuhao,lbxh";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,jt_id,jt_id);
        return Result.ok("查询成功",list);
    }

    //查询详细参数--从布机实时表中查询
    public Result queryXxcs_curBuji(String jt_id){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInInteger(jt_id);
        ProcedureContext pro=baseService.callProcedure("pc_cur_bujicanshu", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //查询报警参数
    public Result queryBjcsByJtid(String jt_id){
        String sql = "select a.value,a.jitai_id,a.param_id,b.danwei,b.name,b.xuhao,b.leibie_id,b.leibie_name,b.lbxh from sys_current as a join " +
                "( " +
                "select a.*,b.name as leibie_name,b.xuhao as lbxh from sys_param a join sys_param_leibie b on a.leibie_id=b.id  " +
                "where a.leibie_id in (  " +
                " select id from sys_param_leibie  " +
                " where jixing_id = ( " +
                " select gongxu_id from base_shebei where id=? " +
                " )  " +
                ")  " +
                "and zhanshi_flag=1 and baojing_flag=1  " +
                ") as b on a.param_id=b.id " +
                "where a.jitai_id=? " +
                "order by xuhao,lbxh";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,jt_id,jt_id);
        return Result.ok("查询成功",list);
    }

    //车间总览数据
    public Result cur_chejianzonglan(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_chejianzonglan");
        return Result.ok("查询成功",proc.getDatas());
    }

    //温湿度数据查询
    public List<Map<String,Object>> cur_wenshidu_findAll(){
        String sql = "select id,cast(last_modified_date as varchar(50)) last_modified_date,name,shidu,wendu,weizhi from sys_wenshidu";
        return jdbcTemplate.queryForList(sql);
    }

    //整经总览
    public Result cur_zhengjing(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_zhengjing");
        return Result.ok("查询成功",proc.getDatas());
    }

    //浆纱总览
    public Result cur_jiangsha(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_jiangsha");
        return Result.ok("查询成功",proc.getDatas());
    }

    //穿综
    public Result cur_chuanzong(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_chuanzong");
        return Result.ok("查询成功",proc.getDatas());
    }

    //了机预测
    public Result cur_liaojiyuce(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_liaojiyuce");
        return Result.ok("查询成功",proc.getDatas());
    }

    //落布预测
    public Result cur_luobuyuce(){
        ProcedureContext proc = baseService.callProcedureWithOutParams("pc_cur_luobuyuce");
        return Result.ok("查询成功",proc.getDatas());
    }

    //历史曲线查询
    public List<History_Param> findLSQX(Long param_id,Long jth_id){
       return historyParamRepository.findByParam(param_id,jth_id);
    }

}
