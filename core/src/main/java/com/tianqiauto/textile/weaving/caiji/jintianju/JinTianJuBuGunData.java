package com.tianqiauto.textile.weaving.caiji.jintianju;

import io.github.biezhi.excel.plus.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author xingxiaoshuai
 * @Date 2019-04-25 09:12
 * @Version 1.0
 **/


@Service
public class JinTianJuBuGunData {

    @Autowired
    private JdbcTemplate jdbcTemplate;


//    @Scheduled(cron = "0 0/1 * * * ?")
    public void getBuGun() {



        String insert = "insert into sys_bugun(changdu,luobushijian,riqi,banci_id,heyuehao_id,jitai_id,luoburen_id,zhizhou_left_id,zhizhou_right_id,lunban_id,shedingchangdu) values(?,?,?,?,?,?,?,?,?,?,?)";

        for (int i = 115; i < 121; i++) {


            try {
                File file_doff = new File("/Users/xingxiaoshuai/Downloads/"+i+"/I_DOFFPRD.CSV");

                String findJitaihao = "select c.* from base_shebei s,sys_current_buji c where s.id=c.jitai_id and jitaihao = ?";
                Map current_map = jdbcTemplate.queryForMap(findJitaihao, "布机"+i);

                List<Model_Doff> list_doff = Reader.create(Model_Doff.class)
                        .from(file_doff)
                        .start(0)
                        .asList();

                String sql = "select top 1 * from base_pb_history where gongxu_id = (select id from base_gongxu where name='织布') and bancijieshushijian > ? and bancikaishishijian < ?";
                for (int j = 0; j < list_doff.size()-1; j++) {
                    Model_Doff model_doff = list_doff.get(j);
                    Map map = jdbcTemplate.queryForMap(sql,model_doff.getTime_computed(),model_doff.getTime_computed());
                    String  findBuGunCount = "select  count(*)  from sys_bugun  where luobushijian = ? and jitai_id = ?";
                    Integer count = jdbcTemplate.queryForObject(findBuGunCount,Integer.class,model_doff.getTime_computed(),current_map.get("jitai_id"));
                    if(count<1){
                        jdbcTemplate.update(insert,model_doff.getBuchang(),model_doff.getTime_computed(),map.get("riqi"),
                        map.get("banci_id"),current_map.get("heyuehao_id"),current_map.get("jitai_id"),current_map.get("user_id"),current_map.get("zhizhou_left_id"),
                        current_map.get("zhizhou_right_id"),map.get("lunban_id"),current_map.get("shedingbuchang"));
                    }


                }

            }catch (Exception e){
                System.out.println("doffer "+i +"   " +new Date()+"  "+e.getMessage());
                e.printStackTrace();
            }

        }



    }



}
