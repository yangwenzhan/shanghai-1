package com.tianqiauto.textile.weaving.util.procedure.core;


import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureParam;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/testResult")
@SuppressWarnings("rawtypes")
public class ProcedureTestController {
    @Autowired
    BaseService baseService;

    /**
     * 现存储过程调用方式一
     * @param startPage
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param lbid
     * @param gcId
     * @return
     */
    @GetMapping(value = "queryBcyzInfo")
    public Result queryBcyzInfo(String startPage, String pageSize, String startTime, String endTime, String lbid, String gcId) {
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.batchInAdd(startPage,pageSize,startTime,endTime,lbid).addOut().batchInAdd(gcId);
        ProcedureContext pro=baseService.callProcedure("PC_PAIBAN_BCYZ", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    /**
     * 现存储过程调用方式二
     * @param startPage
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param lbid
     * @param gcId
     * @return
     */
    @GetMapping(value = "queryBcyzMes")
    public Result queryBcyzMes(String startPage, String pageSize, String startTime, String endTime, String lbid, String gcId) {
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInInteger(startPage);
        ppu.addInInteger(pageSize);
        ppu.addInVarchar(startTime);
        ppu.addInVarchar(endTime);
        ppu.addInVarchar(lbid);
        ppu.addOut();
        ppu.addInVarchar(gcId);
        ProcedureContext pro=baseService.callProcedure("PC_PAIBAN_BCYZ", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    /**
     * 原存储过程调用方式
     * @param startPage
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param lbid
     * @param gcId
     * @return
     */
    @GetMapping(value = "queryBcyz")
    public ProcedureContext queryBcyz(String startPage, String pageSize, String startTime,String endTime,String lbid,String gcId) {
        List<ProcedureParam> pm = new ArrayList<ProcedureParam>();
        ProcedureParam param1 = new ProcedureParam(1, startPage.toString(), Types.INTEGER, "IN");
        ProcedureParam param2 = new ProcedureParam(2, pageSize.toString(), Types.INTEGER, "IN");
        ProcedureParam param3 = new ProcedureParam(3, startTime.equals("") ? null : startTime, Types.VARCHAR, "IN");
        ProcedureParam param4 = new ProcedureParam(4, endTime.equals("") ? null : endTime, Types.VARCHAR, "IN");
        ProcedureParam param5 = new ProcedureParam(5, lbid.equals("") ? null : lbid, Types.VARCHAR, "IN");
        ProcedureParam param6 = new ProcedureParam(6, null, Types.INTEGER, "OUT");
        ProcedureParam param7 = new ProcedureParam(7, gcId.equals("") ? null : gcId, Types.VARCHAR, "IN");
        pm.add(param1);
        pm.add(param2);
        pm.add(param3);
        pm.add(param4);
        pm.add(param5);
        pm.add(param6);
        pm.add(param7);
        return baseService.callProcedure("PC_PAIBAN_BCYZ", pm);
    }


}
