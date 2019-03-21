package com.tianqiauto.textile.weaving.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        String sql = "update base_pb_yunzhuanfangshi_xiangqing set lunban_id=?,banci_id=? where yunzhuanfangshi_id=? and sort=?";
        jdbcTemplate.batchUpdate(sql,list);
    }

    /**
     * 新增运转方式详情
     * @param conditions
     * @return
     */
    @Transactional
    public void add_new_yzfs(String conditions){

        JSONArray conditionArry = JSONArray.parseArray(conditions);

        if(conditionArry.size()>0) {
            //新增运转方式
            JSONObject job1 = conditionArry.getJSONObject(0);
            String yzfsName = job1.getString("yzfs_name");
            String yzfsLb = job1.getString("yzfs_lb");
            String yzfsBc = job1.getString("yzfs_bc");
            String addYzfs_sql = "insert into base_pb_yunzhuanfangshi(name,lunbanshu,paibanshu) values(?,?,?)";
            int result = jdbcTemplate.update(addYzfs_sql, yzfsName, yzfsLb, yzfsBc);

            if (result >= 1) {
                String sel_sql = "select id from base_pb_yunzhuanfangshi where name=?";
                Map<String, Object> map = jdbcTemplate.queryForMap(sel_sql, yzfsName);
                String yzfs_id = map.get("id").toString();

                //插入新的运转详情
                List<Object[]> lb_list = new ArrayList<>();

                JSONArray yzxq_arry = conditionArry.getJSONArray(1);

                for (int i = 0; i < yzxq_arry.size(); i++) {
                    JSONObject job3 = yzxq_arry.getJSONObject(i);
                    String[] arr = new String[4];
                    arr[0] = yzfs_id;
                    arr[1] = job3.getString("pxh");
                    arr[2] = job3.getString("lb_id");
                    arr[3] = job3.getString("bc_id");
//                    arr[4] = job3.getString("bc_kssj");
//                    arr[5] = job3.getString("bc_jssj");
//                    arr[6] = job3.getString("dayDiff");
                    lb_list.add(arr);
                }
                String add_yzfsInfo_sql = "insert into base_pb_yunzhuanfangshi_xiangqing(yunzhuanfangshi_id,sort,lunban_id,banci_id) values(?,?,?,?)";
                jdbcTemplate.batchUpdate(add_yzfsInfo_sql, lb_list);
            }
        }
    }


}
