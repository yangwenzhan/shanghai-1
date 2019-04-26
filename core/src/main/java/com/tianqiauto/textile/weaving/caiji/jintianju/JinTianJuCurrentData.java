package com.tianqiauto.textile.weaving.caiji.jintianju;

import com.tianqiauto.textile.weaving.model.base.SheBei;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Scheduled(cron = "20/30 * * * * ?")
    public void getCurrent(){

        String sql = "select top 1 * from base_pb_history where gongxu_id = (select id from base_gongxu where name='织布') and  bancijieshushijian = ?";


        String insert = "insert into sys_shift_buji([changdu],[riqi],[banci_id],[heyuehao_id],[shebei_id],[jingtingcishu],[jingtingshijian],[qitatingcishu],[qitatingshijian],[weitingcishu],[weitingshijian],[xiaolv],[zongtingcishu],[zongtingshijian],[daweicishu],[jieshushijian],[kaishishijian],[bancijieshushijian],[bancikaishishijian],[create_time],[last_modify_ren],[last_modify_time],[lilunshichang],[luru_ren],[xuhao],[lunban_id],[user_id]) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


        for (int i = 115; i < 121; i++) {
            try {

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

                String findJitaihao = "select c.* from base_shebei s,sys_current_buji c where s.id=c.jitai_id and jitaihao = ?";
                Map current_map = jdbcTemplate.queryForMap(findJitaihao, "布机"+i);

                //运行状态
                String zhuangtai = jdbcTemplate.queryForObject("select dict.id from base_dict dict,base_dict_type type where dict.type_id=type.id and type.name='运行状态' and dict.value = ?",new Object[]{current.getStatus()},String.class);


                String updateCurrent = "update sys_current_buji set   " +
                        "buchang=?,chesu=?,jingting=?,last_modify_time=?," +
                        "liaojishijian=?,luobushijian=?,shedingbuchang=?,weiting=?,zongting=?," +
                        "qitingzhuangtai_id=?,yunxingzhuangtai_id=?," +
                        "daweicishu=?,jingtingshijian=?,weitingshijian=?," +
                        "zongtingshijian=?,yunxingshijian=?,xiaolv=?,dangbanbuchang=?" +
                        " where jitai_id = ?";

                String  getBanciTime = "select DateDiff(s,bancikaishishijian,getdate()) from base_pb_current pb where gongxu_id = (select id from base_gongxu where name='织布')";

                String bancitime = jdbcTemplate.queryForObject(getBanciTime,String.class);  //当班理论时长

                Double xiaolv = current.getYunxingshijian().doubleValue()/(Integer.parseInt(bancitime));



                jdbcTemplate.update(updateCurrent,current.getBuchang(),shift_current.getChesu(),shift_current.getJingtingcishu(),
                        current.getTime_computed(),current.getYujiliaoji(),current.getYujiluobu(),
                        current.getDoffing_length(),shift_current.getWeitingcishu(),shift_current.getZongtimecishu(),
                        zhuangtai,zhuangtai,shift_current.getDaweicishu(),
                        shift_current.getJingtingtime(),shift_current.getWeitingtime(),shift_current.getZongtingtime(),
                        current.getYunxingshijian(),xiaolv,shift_current.getBuchang(),current_map.get("jitai_id"));

//    "insert into sys_shift_buji([changdu],[riqi],[banci_id],[heyuehao_id],[shebei_id],[jingtingcishu],[jingtingshijian],[qitatingcishu],[qitatingshijian],[weitingcishu],[weitingshijian],
//    [xiaolv],[zongtingcishu],[zongtingshijian],[daweicishu],[jieshushijian],
//    [kaishishijian],[bancijieshushijian],[bancikaishishijian],[create_time],[last_modify_ren],[last_modify_time],[lilunshichang],[luru_ren],[xuhao],[lunban_id],[user_id]) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                //归档数据处理
                for (int j = 0; j < list_shift.size()-1; j++) {
                    Model_Shift_Current model_shift_current =list_shift.get(j);
                    Map map = jdbcTemplate.queryForMap(sql,model_shift_current.getTime_computed());
                    String  findShiftCount = "select  count(*)  from sys_shift_buji  where create_time = ? and shebei_id = ?";
                    Integer count = jdbcTemplate.queryForObject(findShiftCount,Integer.class,model_shift_current.getTime_computed(),current_map.get("jitai_id"));
                    if(count<1){
                        jdbcTemplate.update(insert,model_shift_current.getBuchang(),map.get("riqi"),map.get("banci_id"),current_map.get("heyuehao_id"),
                                current_map.get("jitai_id"),model_shift_current.getJingtingcishu(),model_shift_current.getJingtingtime(),null,null,model_shift_current.getWeitingcishu(),model_shift_current.getWeitingtime(),
                                model_shift_current.getXiaolv(),model_shift_current.getZongtimecishu(),model_shift_current.getZongtingtime(),model_shift_current.getDaweicishu(),
                                null,null,map.get("bancijieshushijian"),map.get("bancikaishishijian"),model_shift_current.getTime_computed(),null,null,
                                720,null,null,map.get("lunban_id"),current_map.get("user_id"));
                    }
                }






            }catch (Exception e){
                System.out.println("current "+i +"   " +new Date()+"  "+e.getLocalizedMessage());
                e.printStackTrace();
            }



        }
    }



}
