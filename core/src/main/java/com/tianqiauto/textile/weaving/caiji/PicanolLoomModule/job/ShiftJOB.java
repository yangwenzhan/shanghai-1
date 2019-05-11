package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.Cache;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/27 9:57
 */
@Slf4j
@Component
@EnableScheduling
public class ShiftJOB {

    @Autowired
    private BaseService baseService;

//    @Scheduled(cron = "0 11 07 ? * *")
    private void run(){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        baseService.callProcedure("picanol_shift_bj_job",ppu.getList());
    }

}
