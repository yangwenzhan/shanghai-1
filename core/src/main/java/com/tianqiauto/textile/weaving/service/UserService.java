package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.base.Permission;
import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

        gx_id = StringUtils.isEmpty(gx_id)?null:gx_id;
        lb_id = StringUtils.isEmpty(lb_id)?null:lb_id;
        zu = StringUtils.isEmpty(zu)?null:zu;
        sfzz = StringUtils.isEmpty(sfzz)?null:sfzz;
        js_id = StringUtils.isEmpty(js_id)?null:js_id;
        ghxm = StringUtils.isEmpty(ghxm)?null:ghxm;

        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(gx_id).addInVarchar(lb_id).addInVarchar(zu).addInInteger(sfzz).addInInteger(js_id).addInVarchar(ghxm);
        ProcedureContext pro=baseService.callProcedure("pc_base_yuangong", ppu.getList());
        return Result.ok(pro.getDatas());
    }

    //修改用户
    @Transactional
    public void updateUserInfo(String xm,String birth,String user_yg_id,Long id,int zu,Long gx_id,Long lb_id,boolean flag){
        String sql1 = "update base_user set xingming=?,birthday=?,user_yuangong_id=? where id=?";
        String sql2 = "update base_user_yuangong set zu=?,gongxu_id=?,lunban_id=? where user_id=?";
        String sql3 = "delete from base_user_yuangong where user_id=?";

        jdbcTemplate.update(sql1,xm,birth,user_yg_id,id);
        if(flag){
            jdbcTemplate.update(sql3,id);
        }else{
            jdbcTemplate.update(sql2,zu,gx_id,lb_id,id);
        }
    }

    //设置组
    @Transactional
    public void updateUserZu(String zu,String user_ids){
        String sql1 = "select id from base_user where id in ('"+user_ids+"') and user_yuangong_id is null";
        String sql2 = "select id from base_user where id in ('"+user_ids+"') and user_yuangong_id is not null";
        String sql3 = "update base_user_yuangong set zu=? where user_id=?";
        String sql4 = "insert into base_user_yuangong(zu,user_id) values(?,?)";

        List inst_ids = jdbcTemplate.queryForList(sql1);
        List upd_ids = jdbcTemplate.queryForList(sql2);

        List<Object[]> inst_list = new ArrayList<>();
        for(int i = 0; i<inst_ids.size(); i++){
            String arr[] = new String[2];
            arr[0] = zu;
            arr[1] = inst_ids.get(i).toString();
            inst_list.add(arr);
        }
        jdbcTemplate.batchUpdate(sql4,inst_list);

        List<Object[]> upd_list = new ArrayList<>();
        for(int i=0; i<upd_ids.size(); i++){
            String arr[] = new String[2];
            arr[0] = zu;
            arr[1] = upd_ids.get(i).toString();
            upd_list.add(arr);
        }
        jdbcTemplate.batchUpdate(sql3,upd_list);

    }

    //设置角色
    @Transactional
    public void updateUserRole(String[] user_ids,String[] role_ids){

        //先把用户对应角色删除，再重新insert新的角色
        String sql1 = "delete from base_user_role where user_id=?";
        String sql2 = "insert into base_user_role(user_id,role_id) values(?,?)";

        for(int i=0; i<user_ids.length; i++){
            jdbcTemplate.update(sql1,user_ids[i]);

            List<Object[]> list = new ArrayList<>();
            for (int j = 0; j < role_ids.length; j++) {
                String arr[] = new String[2];
                arr[0] = user_ids[i];
                arr[1] = role_ids[j];
                list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql2,list);
        }

    }





}
