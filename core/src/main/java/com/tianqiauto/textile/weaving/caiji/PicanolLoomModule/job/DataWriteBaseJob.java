package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.job;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.ParamVo;
import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.dao.ParamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * bjw
 * @Date 2019/3/7 10:38
 */
@Slf4j
@Component
@EnableScheduling
public class DataWriteBaseJob { //fixme 继承JOB

    @Autowired
    private ParamDao paramDao;

    /**
     * 每分钟的第0秒执行一次
     * 把缓存数据写入数据库中...
     **/
//    @Scheduled(fixedRate = 10000)
    private void run(){
        long start = System.currentTimeMillis();
        paramDao.batchUpdateParam(ParamVo.getCollection());
        long end = System.currentTimeMillis();
        log.info("数据存入数据库共耗时："+(end-start)+"毫秒！");
    }


}
