package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.dao;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolParam;
import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
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
        String sql = " UPDATE picanol_param SET value = ? , last_modify_time = ? WHERE updateKey = ? ";
        List<Object[]> list = new ArrayList();
        Iterator<PicanolParam> interator = paramList.iterator();
        while (interator.hasNext()){
            PicanolParam pp = interator.next();
            Object[] array = {pp.getValue(),pp.getLastModifyTime(),pp.getMachineNumber()+pp.getParamNumber()};
            list.add(array);
        }
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(sql,list);
        long end = System.currentTimeMillis();
        log.info("存入库耗时："+(end-start)+"毫秒！");
    }

    public void saveAll_Current_BuJi(List<Current_BuJi> cbjs) {
        String sql = " UPDATE sys_current_buji set \n" +
                "buchang = ?\n" +
                ",chesu = ?\n" +
                ",jingting = ?\n" +
                ",last_modify_time = ?\n" +
                ",onlineflag = ?\n" +
                ",shedingbuchang = ?\n" +
                ",weiting = ?\n" +
                ",zongting = ?\n" +
                ",qitingzhuangtai_id = ?\n" +
                ",yunxingzhaungtai_id = ?\n" +
                ",daweicishu = ?\n" +
                ",weimi = ?\n" +
                ",yunxingshijian = ?\n" +
                "WHERE id = ? ";
        List<Object[]> list = new ArrayList();
        for (Current_BuJi bj: cbjs){
            Object[] array = {
                    bj.getBuchang(),
                    bj.getChesu(),
                    bj.getJingting(),
                    bj.getLastModifyTime(),
                    bj.getOnlineflag(),
                    bj.getShedingbuchang(),
                    bj.getWeiting(),
                    bj.getZongting(),
                    bj.getQitingzhuangtai().getId(),
                    bj.getYunxingzhuangtai().getId(),
                    bj.getDaweicishu(),
                    bj.getWeimi(),
                    bj.getYunxingshijian(),
                    bj.getId()
            };
            list.add(array);
        }
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(sql,list);
        long end = System.currentTimeMillis();
        log.info("布机事实表-存入库耗时："+(end-start)+"毫秒！");

    }
}
