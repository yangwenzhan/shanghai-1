package com.tianqiauto.textile.weaving.caiji.wenshidu;

import com.tianqiauto.textile.weaving.caiji.wenshidu.dao.WenshiduRepository;
import com.tianqiauto.textile.weaving.caiji.wenshidu.service.CaijiService;
import com.tianqiauto.textile.weaving.caiji.wenshidu.utils.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author bjw
 * @Date 2019/5/11 9:10
 */
@Slf4j
@Component
@Order(value = 2)
public class WenShiDuStartListener implements CommandLineRunner {

    @Resource
    private WenshiduRepository wenshiduRepository;

    @Autowired
    private CaijiService caijiService;

    @Override
    public void run(String... args) throws Exception {
        cacheInit();
        caiji();
    }

    private void cacheInit(){
        Cache.wenShiDus = wenshiduRepository.findAll();
        log.info("温湿度 Cache initialization succeeded...");
    }

    private void caiji() throws InterruptedException {
        for (;true;){
            try{
                Long start = System.currentTimeMillis();
                caijiService.caiji();
                Long end = System.currentTimeMillis();
                log.info("温湿度采集耗时："+(end-start)+"毫秒！");
            }catch (Exception e){
                log.error("采集程序异常："+e.getMessage());
            }
        }
    }
}
