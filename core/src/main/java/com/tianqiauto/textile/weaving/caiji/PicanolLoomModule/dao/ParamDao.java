package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.dao;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/7 14:49
 */
@Slf4j
@Repository
public class ParamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchUpdateParam(Collection<PicanolParam> paramList) {
        String sql = " UPDATE picanol_param SET value = ? , last_modify_time = GETDATE() WHERE updateKey = ? ";
        List<Object[]> list = new ArrayList();
        Iterator<PicanolParam> interator = paramList.iterator();
        while (interator.hasNext()){
            PicanolParam pp = interator.next();
            Object[] array = {pp.getValue(),pp.getMachineNumber()+pp.getName()};
            list.add(array);
        }
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(sql,list);
        long end = System.currentTimeMillis();
        log.info("存入库耗时："+(end-start)+"毫秒！");
    }

}
