package com.tianqiauto.textile.weaving.caiji.wenshidu.job;

import com.tianqiauto.textile.weaving.caiji.wenshidu.dao.WenshiduDao;
import com.tianqiauto.textile.weaving.caiji.wenshidu.service.CaijiService;
import com.tianqiauto.textile.weaving.caiji.wenshidu.utils.Cache;
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
public class RunJob {

    @Autowired
    private WenshiduDao wenshiduDao;

    /**
     * 存入数据库
     * @Author bjw
     * @Date 2019/5/11 11:08
     **/
    @Scheduled(fixedRate=40000)
    private void update() {
        Long start = System.currentTimeMillis();
        wenshiduDao.batchUpdateParam(Cache.wenShiDus);
        Long end = System.currentTimeMillis();
        log.info("温湿度存入数据库耗时："+(end-start)+"毫秒！");

    }
}
