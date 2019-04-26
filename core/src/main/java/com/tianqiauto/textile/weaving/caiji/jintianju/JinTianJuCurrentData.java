package com.tianqiauto.textile.weaving.caiji.jintianju;

import com.tianqiauto.textile.weaving.model.base.SheBei;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CurrentDataService
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-25 09:12
 * @Version 1.0
 **/


@Service
public class JinTianJuCurrentData {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //更新实时数据
    public void getCurrent(){

        long begin = System.currentTimeMillis();

        for (int i = 115; i < 121; i++) {
            File file_shift = new File("/Users/xingxiaoshuai/Downloads/"+i+"/I_SHIFTPRD.CSV");
            File file_status = new File("/Users/xingxiaoshuai/Downloads/"+i+"/I_STATUS.CSV");

            List<Model_Shift_Current> list_shift = Reader.create(Model_Shift_Current.class)
                    .from(file_shift)
                    .start(0)
                    .asList();
            Model_Shift_Current shift_current = list_shift.get(list_shift.size() - 1);

            List<Model_Current> list_current = Reader.create(Model_Current.class)
                    .from(file_status)
                    .start(0)
                    .asList();
            Model_Current current = list_current.get(0);

            String findJitaihao = "select jitai_id from sys_shebei s,sys_current_buji c where s.id=c.jitai_id and jitaihao = ?";
            String jitai_id = jdbcTemplate.queryForObject(findJitaihao,new Object[]{"布机"+i},String.class);

            String updateCurrent = "update sys_current_buji set   " +
                    "buchang=?,chesu=?,jingting=?,last_modify_time=?," +
                    "liaojishijian=?,luobushijian=?,shedingbuchang=?,weiting=?,zongting=?," +
                    "qitingzhuangtai_id=?,yunxingzhuangtai_id=?," +
                    "daweicishu=?,jingtingshijian=?,weitingshijian=?," +
                    "zongtingshijian=?,yunxingshijian=?" +
                    " where jitai_id = ?";

            jdbcTemplate.update(updateCurrent,current.getBuchang(),shift_current.getChesu(),shift_current.getJingtingcishu(),
                    current.getTime_computed(),current.getYujiliaoji(),current.getYujiluobu(),
                    current.getDoffing_length(),shift_current.getWeitingcishu(),shift_current.getZongtimecishu(),
                    current.getStatus(),current.getStatus(),shift_current.getDaweicishu(),
                    shift_current.getJingtingtime(),shift_current.getWeitingtime(),shift_current.getZongtingtime(),
                    current.getYunxingshijian(),jitai_id);

            System.out.println("used:" + (System.currentTimeMillis() - begin) + "mills");

        }
    }



}
