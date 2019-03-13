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

    /**
     * 每分钟的第0秒执行一次
     * 读取打纬次数及布长
     * @Date 2019/3/11 8:51
     **/
//    @Scheduled(cron="0 0/1 * * * ?")
    private void run030(){
        run(pcn030Thread);
    }

    /**
     * 每分钟的第10秒执行一次
     * 读取最近6个班的数据
     * @Date 2019/3/11 8:51
     **/
//    @Scheduled(cron="10 0/1 * * * ?")
    private void run031(){
        run(pcn031Thread);
    }

    /**
     * 每分钟的第20秒执行一次
     * 请求获取机器速度
     * @Date 2019/3/11 8:51
     **/
//    @Scheduled(cron="20 0/1 * * * ?")
    private void run110(){
        run(pcn110Thread);
    }

    /**
     * 每分钟的第30秒执行一次
     * 请求获取打纬密度
     * @Date 2019/3/11 8:51
     **/
//    @Scheduled(cron="30 0/1 * * * ?")
    private void run130(){
        run(pcn130Thread);
    }

    private void run(AbstractAnalysis abstractAnalysis) {
        List<List<PicanolHost>> hostLists = Cache.picanolHost;
        for(List<PicanolHost> list : hostLists){
            abstractAnalysis.init(list);
        }
    }
}
