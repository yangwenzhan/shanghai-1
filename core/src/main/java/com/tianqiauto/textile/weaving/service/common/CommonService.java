package com.tianqiauto.textile.weaving.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CommonService
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/25 15:14
 * @Version 1.0
 **/
@Service
public class CommonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> findUserZu(){
        String sql = "select distinct zu from base_user_yuangong";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> findCSLB(String gongxu,String jixing){
        String sql = "select * from sys_param_leibie where gongxu_id=isnull(?,gongxu_id) and jixing_id=isnull(?,jixing_id)";
        return jdbcTemplate.queryForList(sql, StringUtils.isEmpty(gongxu)?null:gongxu,StringUtils.isEmpty(jixing)?null:jixing);
    }

    public List<Map<String,Object>> findZhiJiJiXing(){
        String sql = "select * from base_gongxu where parent_id=(select id from base_gongxu where name='织布')";
        return jdbcTemplate.queryForList(sql);
    }


}
