package com.tianqiauto.textile.weaving.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void updateRolePermission(String role_id,String[] permission_ids){
        String sql1 = "delete from base_role_permission where role_id=?";
        String sql2 = "insert into base_role_permission(role_id,permission_id) values(?,?)";
        //删除该角色id对应的资源权限
        jdbcTemplate.update(sql1,role_id);
        //新增
        List<Object[]> list = new ArrayList<>();
        for(int i =0; i<permission_ids.length; i++){
            String[] arr = new String[2];
            arr[0] = role_id;
            arr[1] = permission_ids[i];
            list.add(arr);
        }
        jdbcTemplate.batchUpdate(sql2,list);

    }



}
