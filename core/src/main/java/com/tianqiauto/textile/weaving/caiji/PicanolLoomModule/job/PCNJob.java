package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job.analysis.*;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * bjw
 * @Date 2019/3/7 10:38
 */
@Slf4j
@Component
@EnableScheduling
public class PCNJob { //fixme 继承JOB

    @Autowired
    private PCN030Thread pcn030Thread;
    @Autowired
    private PCN031Thread pcn031Thread;
    @Autowired
    private PCN110Thread pcn110Thread;
    @Autowired
    private PCN130Thread pcn130Thread;

//    @Scheduled(fixedRate=100000)
    private void run() throws InterruptedException {
        List<List<PicanolHost>> hostLists = Cache.picanolHost;
        for(List<PicanolHost> list : hostLists){
            pcn030Thread.init(list);
            Thread.sleep(3000);
            pcn031Thread.init(list);
            Thread.sleep(3000);
            pcn110Thread.init(list);
            Thread.sleep(3000);
            pcn130Thread.init(list);
        }
    }
}
