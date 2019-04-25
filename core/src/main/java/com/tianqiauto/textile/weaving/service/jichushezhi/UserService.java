package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Permission;
import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-27 20:40
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private BaseService baseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Permission> getPermissions(Long id){
        List<Permission>  list = new ArrayList<>();
        User user = userRepository.getOne(id);
        for(Role role : user.getRoles()){
           for(Permission permission : role.getPermissions()){
               if(!list.contains(permission)){
                   list.add(permission);
               }
           }
        }
        return list;
    }

    public List<Permission> getPermissions(User user){
        List<Permission>  list = new ArrayList<>();
        for(Role role : user.getRoles()){
           for(Permission permission : role.getPermissions()){
               if(!list.contains(permission)){
                   list.add(permission);
               }
           }
        }
        return list;
    }

    //查询用户
    public Result findAllUser(String gx_id, String lb_id, String zu, String sfzz, String js_id, String ghxm){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(gx_id).addInVarchar(lb_id).addInVarchar(zu).addInInteger(sfzz).addInInteger(js_id).addInVarchar(ghxm);
        ProcedureContext pro=baseService.callProcedure("pc_base_yuangong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //修改用户
    @Transactional
    public void updateUserInfo(String xm,String birth,String sex,String email,String phone,
                               Long id,String zu,
                               String gx_id,String lb_id,Set<Role> roles,boolean flag){


        String sql1 = "update base_user set xingming=?,birthday=?,user_yuangong_id=?,sex=?,mobile=?,email=? where id=?";
        String sql2 = "update base_user_yuangong set zu=?,gongxu_id=?,lunban_id=? where user_id=?";
        String sql3 = "delete from base_user_yuangong where user_id=?";

        String sql4 = "delete from base_user_role where user_id=?";
        String sql5 = "insert into base_user_role(user_id,role_id) values(?,?)";

        String sql6 = "update base_user set user_yuangong_id=null where id in " +
                " (select user_id from base_user_yuangong where zu is null and gongxu_id is null and lunban_id is null)";

        String sql8 = "insert into base_user_yuangong(zu,gongxu_id,lunban_id,user_id) values(?,?,?,?)";
        String sql9 = "select id from base_user_yuangong where user_id=?";

        List<Map<String,Object>> ls = jdbcTemplate.queryForList(sql9,id);

        if(flag){
            //flag true:说明没有base_user_yuangong表数据关联,将user表user_yuangong_id置为空
            jdbcTemplate.update(sql1, xm, birth, null, sex, phone, email, id);
            jdbcTemplate.update(sql3,id);
        }else{
            //flag false:说明有base_user_yuangong表数据关联，
            //分两种情况，insert和update base_user_yuangong,insert后还需把base_user表对应的user_yuangong_id更新
            if(ls.size()>0){
                //base_user_yuangong表中有数据，只需update操作
                String user_yg_id = ls.get(0).get("id").toString();
                jdbcTemplate.update(sql1, xm, birth, user_yg_id, sex, phone, email, id);
                jdbcTemplate.update(sql2,zu,gx_id,lb_id,id);
            }else{
                //base_user_yuangong表中无数据，需insert操作
                jdbcTemplate.update(sql8,zu,gx_id,lb_id,id);
                jdbcTemplate.update(sql6);
            }
        }

        if(roles.size()>0){
            jdbcTemplate.update(sql4,id);
            List<Object[]> list = new ArrayList<>();
            for(Role role:roles){
                String[] arr = new String[2];
                arr[0] = Long.toString(id);
                arr[1] = role.getId().toString();
                list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql5,list);
        }
    }


    //新增时设置角色
    @Transactional
    public void addUser_setRole(String id, Set<Role> roles){
        String sql = "insert into base_user_role(user_id,role_id) values(?,?)";
        List<Object[]> list = new ArrayList<>();
        for(Role role:roles){
            String[] arr = new String[2];
            arr[0] = id;
            arr[1] = role.getId().toString();
            list.add(arr);
        }
        jdbcTemplate.batchUpdate(sql,list);
    }


    //修改员工基本信息
    public void setUserInfo(String id,String xingming,String sex,String birthday,String mobile,String email){
        String sql = "update base_user set xingming=?,sex=?,mobile=?,email=?,birthday=? where id=?";
        jdbcTemplate.update(sql,xingming,sex,mobile,email,birthday,id);
    }

    //查询旧密码
    public Map<String,Object> getPwd(Long id){
        String sql = "select password from base_user where id=?";
        return jdbcTemplate.queryForMap(sql,id);
    }

    @Transactional
    public User saveUser(User user){
       return userRepository.save(user);
    }


    public List<User> getByZaizhi(Integer sfzz) {
        String sql = "SELECT id,birthday,username,xingming,sex,mobile,email,version,create_date,created_by,modified_by,zu,zaizhi FROM base_user WHERE shifouzaizhi = ?";
        List<User> users = jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),sfzz);
        return users;
    }
}
