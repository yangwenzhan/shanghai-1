package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeYueHaoDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Order> findByOrderid(Long orderId) {
        String sql = "SELECT * FROM sys_heyuehao WHERE order_id = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Order>(Order.class),orderId);
    }
}
