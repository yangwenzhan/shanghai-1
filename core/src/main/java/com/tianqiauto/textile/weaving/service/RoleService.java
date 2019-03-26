package com.tianqiauto.textile.weaving.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    //角色删除
    @Transactional
    public void deleteRole(String role_id){
        String sql1="delete from base_user_role where role_id=?";
        String sql2="delete from base_role_permission where role_id=?";
        String sql3="delete from base_role where id=?";
        jdbcTemplate.update(sql1,role_id);
        jdbcTemplate.update(sql2,role_id);
        jdbcTemplate.update(sql3,role_id);
    }

    //将用户从角色中移除
    public void deleteUserFromRole(String role_id,String user_id){
        String sql = "delete from base_user_role where user_id=? and role_id=?";
        jdbcTemplate.update(sql,user_id,role_id);
    }


    public List<Map<String,Object>> getTree(String id){
        List<Map<String,Object>> list = getAllNotes();
        List<Map<String,Object>> zyjs_list = getZYJSDY(id);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < zyjs_list.size(); j++) {
                if (list.get(i).get("id").equals(zyjs_list.get(j).get("permission_id"))) {
                    list.get(i).put("checked", "true");
                    list.get(i).put("open", "true");
                }
            }
        }
        return list;
    }

    /**
     * 获取所有的资源
     * @return
     */
    public List<Map<String,Object>> getAllNotes(){
        String sql = "select id,permission_name as name,parent_id as pId,permission_code from base_permission";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 获取该角色id对应的资源权限
     * @param id
     * @return
     */
    public List<Map<String,Object>> getZYJSDY(String id){
        String sql = "select role_id,permission_id from base_role_permission where role_id=?";
        return jdbcTemplate.queryForList(sql,id);
    }


}
