package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.base.User_YuanGong;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.UserYuanGongRepository;
import com.tianqiauto.textile.weaving.service.UserService;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureResult;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-08 22:52
 * @Version 1.0
 **/

@RestController
@RequestMapping("jichushezhi/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userJpaRepository;

    @Autowired
    private UserYuanGongRepository userYuanGongRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("findAllUser")
    @ApiOperation(value = "查询所有用户信息")
    public Result findAllUser(String gx_id, String lb_id, String zu, String sfzz,String js_id,String ghxm){
        ProcedureResult users = userService.findAllUser(gx_id, lb_id, zu, sfzz, js_id, ghxm);
        return Result.ok("查询成功",users);
    }

    @PostMapping("saveUser")
    @ApiOperation(value = "新增用户")
    public Result saveUser(@Valid @RequestBody User user, @RequestBody User_YuanGong user_yuanGong){
        String pwd = user.getPassword();
        String encryptPwd = passwordEncoder.encode(pwd);
        user.setPassword(encryptPwd);
        User newUser = userJpaRepository.save(user);

        //判断user_yuangong是否为空，若全是空，不进行操作
        Boolean flag = (user_yuanGong.getZu()==null || user_yuanGong.getZu().equals(""))
                && (user_yuanGong.getLunban()==null || user_yuanGong.getLunban().equals(""))
                && (user_yuanGong.getGongxu()==null || user_yuanGong.getGongxu().equals(""));

        if(!flag){

            User_YuanGong newUser_yuanGong = userYuanGongRepository.save(user_yuanGong);

            newUser.setUser_yuanGong(newUser_yuanGong);
            userJpaRepository.save(newUser);

            newUser_yuanGong.setUser(newUser);
            userYuanGongRepository.save(newUser_yuanGong);
        }

        return Result.ok("新增成功!",newUser);
    }

    @PostMapping("updateUserInfo")
    @ApiOperation(value = "修改用户信息",notes = "工号不可修改,姓名不能为空")
    public Result updateUserInfo(@RequestBody User user,@RequestBody User_YuanGong user_yuanGong){

        //判断user_yuangong是否为空 true 全为空
        Boolean flag = (user_yuanGong.getZu()==null || user_yuanGong.getZu().equals(""))
                && (user_yuanGong.getLunban()==null || user_yuanGong.getLunban().equals(""))
                && (user_yuanGong.getGongxu()==null || user_yuanGong.getGongxu().equals(""));

        String xm = user.getXingming();
        Date birth = user.getBirthday();
        String birthday = StringUtils.isEmpty(birth)?null:birth.toString();

        String user_yg_id = null;

        if(!flag){
            user_yg_id = user.getUser_yuanGong().getId().toString();
        }

        int zu = user_yuanGong.getZu();
        Long gx_id = user_yuanGong.getGongxu().getId();
        Long lb_id = user_yuanGong.getLunban().getId();

        userService.updateUserInfo(xm,birthday,user_yg_id,user.getId(),zu,gx_id,lb_id,flag);

        return Result.ok("修改成功!",user);
    }

    @GetMapping("updateUserZaiZhi")
    @ApiOperation(value = "修改员工在职离职")
    public Result updateUserZaiZhi(int zaizhi,Long user_id){
        User user = userJpaRepository.updateUserZaiZhi(zaizhi, user_id);
        return Result.ok("修改成功!",user);
    }

    @GetMapping("updateUserPwd")
    @ApiOperation(value = "重置密码")
    public Result updateUserPwd(String pwd,Long user_id){
        String encryptPwd = passwordEncoder.encode(pwd);
        User user = userJpaRepository.updateUserPwd(encryptPwd,user_id);
        return Result.ok("重置密码成功!",user);
    }

    //设置组
    @PostMapping("updateUserZu")
    @ApiOperation(value = "设置组")
    public Result updateUserZu(String zu,String user_ids){
        userService.updateUserZu(zu, user_ids);
        return Result.ok("设置组成功!",true);
    }

    //设置角色
    @PostMapping("updateUserRole")
    @ApiOperation(value = "设置角色")
    public Result updateUserRole(String[] user_ids,String[] role_ids){
        userService.updateUserRole(user_ids,role_ids);
        return Result.ok("设置角色成功!",true);
    }



}
