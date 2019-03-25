package com.tianqiauto.textile.weaving.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/23 16:44
 * @Version 1.0
 **/
@Service
public class PermissionService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> getTree(){
        List<Map<String,Object>> list = getAllNotes();
        Map<String, Object> map = list.get(0);
        map.put("open", "true");
        map.put("checked", "true");
        return list;
    }

    public List<Map<String,Object>> getSelectTree(){
        List<Map<String,Object>> list = getAllNotes();
        Map<String, Object> map = list.get(0);
        map.put("open", "true");
        map.put("checked", "true");

        for(int i=0; i<list.size(); i++){
            String id = list.get(i).get("id").toString();
            List<Map<String,Object>> list_children = new ArrayList<>();
            for(int j=0; j<list.size(); j++){

                String pid = list.get(j).get("pId").toString();
                if(id.equals(pid)){
                    Map<String, Object> map_children = new HashMap<>();
                    map_children.put("id",list.get(j).get("id").toString());
                    map_children.put("name",list.get(j).get("name").toString());
                    map_children.put("open",true);
                    map_children.put("checked",true);
                    list_children.add(map_children);
                }
            }
            if(list_children.size()>0){
                map.put("children",list_children);
            }
        }
        return list;
    }


    public List<Map<String,Object>> getAllNotes(){
        String sql = "select id,permission_name as name,parent_id as pId,permission_code from base_permission";
        return jdbcTemplate.queryForList(sql);
    }

}
