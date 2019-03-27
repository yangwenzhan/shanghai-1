package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_GongXu_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaiBanService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YunZhuanFangShi_GongXu_Repository yunZhuanFangShi_gongXu_repository;


    //修改运转方式详情
    @Transactional
    public void upd_yzfs_Info(List<Object[]> list){
        String sql = "update base_pb_yunzhuanfangshi_xiangqing set lunban_id=? where yunzhuanfangshi_id=? and sort=?";
        jdbcTemplate.batchUpdate(sql,list);
    }

    /**
     * 新增运转方式详情
     * @return
     */
    @Transactional
    public void add_new_yzfs(Map<String,Object> paibanName,List<Map<String,Object>> banciTime,List<Map<String,Object>> paibanRole){

        String yzfsName = paibanName.get("yzfs_name").toString();
        String yzfsLb = paibanName.get("yzfs_lb").toString();
        String yzfsBc = paibanName.get("yzfs_bc").toString();
        String addYzfs_sql = "insert into base_pb_yunzhuanfangshi(name,lunbanshu,paibanshu) values(?,?,?)";
        int result = jdbcTemplate.update(addYzfs_sql, yzfsName, yzfsLb, yzfsBc);

        if (result >= 1) {
            String sel_sql = "select id from base_pb_yunzhuanfangshi where name=?";
            Map<String, Object> map = jdbcTemplate.queryForMap(sel_sql, yzfsName);
            String yzfs_id = map.get("id").toString();

            //插入新的运转详情
            List<Object[]> lb_list = new ArrayList<>();
            for (int i = 0; i < banciTime.size(); i++) {
                Map<String,Object> pbRoleMap = banciTime.get(i);
                String[] arr = new String[4];
                arr[0] = yzfs_id;
                arr[1] = pbRoleMap.get("pxh").toString();
                arr[2] = pbRoleMap.get("lb_id").toString();
                arr[3] = pbRoleMap.get("bc_id").toString();
                lb_list.add(arr);
            }
            String add_yzfsInfo_sql = "insert into base_pb_yunzhuanfangshi_xiangqing(yunzhuanfangshi_id,sort,lunban_id,banci_id) values(?,?,?,?)";
            jdbcTemplate.batchUpdate(add_yzfsInfo_sql, lb_list);
        }

        //更改班次开始结束时间
        List<Object[]> bc_list = new ArrayList<>();

        for (int i = 0; i < banciTime.size(); i++) {
            Map<String,Object> bcMap = banciTime.get(i);
            String[] arr = new String[3];
            arr[0] = bcMap.get("kssj").toString();
            arr[1] = bcMap.get("jssj").toString();
            arr[2] = bcMap.get("bcid").toString();
            bc_list.add(arr);
        }

        String updBcTime_sql = "update sys_banci set Start_time=?,End_time=? where id=?";

        jdbcTemplate.batchUpdate(updBcTime_sql, bc_list);

    }


}
