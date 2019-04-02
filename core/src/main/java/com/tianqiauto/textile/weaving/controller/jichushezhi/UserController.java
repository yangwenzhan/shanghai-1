package com.tianqiauto.textile.weaving.controller.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.result.ResultUser;
import com.tianqiauto.textile.weaving.model.search.SearchUser;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.service.jichushezhi.UserService;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import com.tianqiauto.textile.weaving.util.log.Logger;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
    private PasswordEncoder passwordEncoder;

//    @GetMapping("findAllUser")
//    @ApiOperation(value = "查询所有用户信息")
//    public Result findAllUser(String gx_id, String lb_id, String zu, String sfzz,String js_id,String ghxm){
//        return  userService.findAllUser(gx_id, lb_id, zu, sfzz, js_id, ghxm);
//
//    }






    public Specification getSpecification(SearchUser searchUser){
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(searchUser.getGongxu())) {
                predicates.add(criteriaBuilder.equal(root.join("gongxu",JoinType.LEFT).get("id"), searchUser.getGongxu()));
            }if(!StringUtils.isEmpty(searchUser.getLunban())) {
                predicates.add(criteriaBuilder.equal(root.join("lunban",JoinType.LEFT).get("id").as(Long.class), searchUser.getLunban()));
            }if(!StringUtils.isEmpty(searchUser.getRole())) {
                predicates.add(criteriaBuilder.equal(root.join("roles",JoinType.LEFT).get("id").as(Long.class),searchUser.getRole()));
            }if(!StringUtils.isEmpty(searchUser.getZu())) {
                predicates.add(criteriaBuilder.equal(root.get("zu"),searchUser.getZu()));
            }if(!StringUtils.isEmpty(searchUser.getShifouzaizhi())) {
                predicates.add(criteriaBuilder.equal(root.get("shifouzaizhi"),searchUser.getShifouzaizhi()));
            }if(!StringUtils.isEmpty(searchUser.getUsername())) {
                predicates.add(criteriaBuilder.equal(root.get("username"),searchUser.getUsername()));
            }
                predicates.add(criteriaBuilder.notEqual(root.get("username"),"admin"));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            };
    }

    @GetMapping("findAllUser")
    @ApiOperation(value = "查询所有用户信息")
    public Result findAllUser(SearchUser searchUser,@PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable){
        System.out.println("排序："+pageable.getSort());
        Page<User> userPage = userJpaRepository.findAll(getSpecification(searchUser), pageable);
        return Result.ok(ResultUser.convert(userPage));
    }

    @PostMapping("saveUser")
    @ApiOperation(value = "新增用户")
    public Result saveUser(@RequestBody User user){

        //判断工号是否唯一
        boolean exist = userJpaRepository.existsByUsername(user.getUsername());
        if(exist){
            return Result.error("工号已存在!",user);
        }
        user.setPassword(passwordEncoder.encode("123456")); //初始密码为123456

        User newUser = userService.saveUser(user);
        return Result.ok("新增成功!",newUser);
    }

    @PostMapping("updateUserInfo")
    @ApiOperation(value = "修改用户信息-jpa语句修改",notes = "工号不可修改,姓名不能为空")
    public Result updateUserInfo(@RequestBody User user){
        User userInDB = userJpaRepository.getOne(user.getId());
        MyCopyProperties.copyProperties(user,userInDB, Arrays.asList("birthday","email","user_yuanGong","mobile","roles","sex","xingming"));

        return Result.ok("修改成功!",userJpaRepository.save(userInDB));
    }


    @GetMapping("updateUserZaiZhi")
    @ApiOperation(value = "修改员工在职离职")
    public Result updateUserZaiZhi(int zaizhi,Long user_id){
        userJpaRepository.updateUserZaiZhi(zaizhi, user_id);
        return Result.ok("修改成功!",user_id);
    }

    @GetMapping("updateUserPwd")
    @ApiOperation(value = "重置密码")
    public Result updateUserPwd(String pwd,Long user_id){
        String encryptPwd = passwordEncoder.encode(pwd);
        userJpaRepository.updateUserPwd(encryptPwd,user_id);
        return Result.ok("重置密码成功!",user_id);
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

    @GetMapping("findByUserId")
    @ApiOperation(value = "根据用户id查询信息")
    public Result findByUserId(Long id){
        User user = userJpaRepository.findAllById(id);
        return Result.ok("查询成功!",user);
    }

    @GetMapping("setUserInfo")
    @ApiOperation(value = "修改当前登录用户基本资料")
    public Result setUserInfo(String id,String xingming,String sex,String birthday,String mobile,String email){
        birthday = StringUtils.isEmpty(birthday)?null:birthday;
        mobile = StringUtils.isEmpty(mobile)?null:mobile;
        email = StringUtils.isEmpty(email)?null:email;
        userService.setUserInfo(id, xingming, sex, birthday, mobile, email);
        return Result.ok("修改成功!",id);
    }

    @GetMapping("resetPwd")
    @ApiOperation(value = "重置密码")
    public Result resetPwd(String id,String oldpwd,String newpwd){

        //新密码加密
        String encryptPwd = passwordEncoder.encode(newpwd);
        Map<String,Object> map = userService.getPwd(id);
        //旧密码加密
        String sql_oldpwd = passwordEncoder.encode(map.get("password").toString());
        //新旧密码对比
        boolean flag = passwordEncoder.matches(sql_oldpwd,encryptPwd);
        if(flag){
            userService.updateUserPwd(id,encryptPwd);
            return Result.ok("密码修改成功!",id);
        }else{
            return Result.error("原始密码输入错误!",id);
        }
    }


}
