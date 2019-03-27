package com.tianqiauto.textile.weaving.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

}
