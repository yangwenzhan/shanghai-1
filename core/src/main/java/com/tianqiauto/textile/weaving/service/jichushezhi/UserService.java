package com.tianqiauto.textile.weaving.service.jichushezhi;

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
                String arr[] = new String[2];
                arr[0] = Long.toString(id);
                arr[1] = role.getId().toString();
                list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql5,list);
        }
    }

    //设置组
    @Transactional
    public void updateUserZu(String zu,String user_ids){
        zu = StringUtils.isEmpty(zu)?null:zu;

        String sql1 = "select id from base_user where id in (select col from SplitIn('"+user_ids+"',',')) and user_yuangong_id is null";
        String sql2 = "select id from base_user where id in (select col from SplitIn('"+user_ids+"',',')) and user_yuangong_id is not null";
        String sql3 = "update base_user_yuangong set zu=? where user_id=?";
        String sql4 = "insert into base_user_yuangong(zu,user_id) values(?,?)";
        String sql5 = "update b set user_yuangong_id=a.id from base_user_yuangong a,base_user b where a.user_id=b.id and b.user_yuangong_id is null";
        String sql6 = "update base_user set user_yuangong_id=null where id in " +
                " (select user_id from base_user_yuangong where zu is null and gongxu_id is null and lunban_id is null)";
        String sql7 = "delete from base_user_yuangong where zu is null and gongxu_id is null and lunban_id is null";


        List<Map<String,Object>> inst_ids = jdbcTemplate.queryForList(sql1);
        List<Map<String,Object>> upd_ids = jdbcTemplate.queryForList(sql2);
        /**
         * 需要insert到base_user_yuangong表中的数据，并把base_user表对应的user_yuangong_id更新
         */
        if(inst_ids.size()>0){
            List<Object[]> inst_list = new ArrayList<>();
            for (Map<String, Object> map : inst_ids) {
                String arr[] = new String[2];
                arr[0] = zu;
                arr[1] = map.get("id").toString();
                inst_list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql4,inst_list);
            jdbcTemplate.update(sql5);
        }

        /**
         *需要更新base_user_yuangong表中zu字段，查找并删除全部为空的记录，更新base_user表user_yuangong_id为空
         */
        if(upd_ids.size()>0){
            List<Object[]> upd_list = new ArrayList<>();
            for (Map<String, Object> map : upd_ids) {
                String arr[] = new String[2];
                arr[0] = zu;
                arr[1] = map.get("id").toString();
                upd_list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql3,upd_list);
            jdbcTemplate.update(sql6);
            jdbcTemplate.update(sql7);
        }

    }

    //设置角色
    @Transactional
    public void updateUserRole(String[] user_ids,String[] role_ids){

        //先把用户对应角色删除，再重新insert新的角色
        String sql1 = "delete from base_user_role where user_id=?";
        String sql2 = "insert into base_user_role(user_id,role_id) values(?,?)";

        if(role_ids.length<=0){
            for(int i=0; i<user_ids.length; i++){
                jdbcTemplate.update(sql1,user_ids[i]);
            }
        }else{
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

    //新增时设置角色
    @Transactional
    public void addUser_setRole(String id, Set<Role> roles){
        String sql = "insert into base_user_role(user_id,role_id) values(?,?)";
        List<Object[]> list = new ArrayList<>();
        for(Role role:roles){
            String arr[] = new String[2];
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
    public Map<String,Object> getPwd(String id){
        String sql = "select password from base_user where id=?";
        return jdbcTemplate.queryForMap(sql,id);
    }

    //修改密码
    public int updateUserPwd(String id,String newpwd){
        String sql = "update base_user set password=? where id=?";
        return jdbcTemplate.update(sql,newpwd,sql);
    }



}
