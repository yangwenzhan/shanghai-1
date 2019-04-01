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
    @ApiOperation(value = "修改用户信息",notes = "工号不可修改,姓名不能为空")
    public Result updateUserInfo(@RequestBody User user){

        User userInDB = userJpaRepository.getOne(user.getId());
        MyCopyProperties.copyProperties(user,userInDB, Arrays.asList("birthday","email","user_yuanGong","mobile","roles","sex","xingming"));
        userJpaRepository.save(userInDB);


//        User_YuanGong user_yuanGong= user.getUser_yuanGong();
//        Set<Role> roles = user.getRoles();
//
//        //判断user_yuangong是否为空 true 空
//        boolean flag = (StringUtils.isEmpty(user_yuanGong.getZu())
//                && StringUtils.isEmpty(user_yuanGong.getGongxu().getId())
//                && StringUtils.isEmpty(user_yuanGong.getLunban().getId()));
//
//        String xm = user.getXingming();
//
//        //判断生日是否为空
//        String birthday = null;
//        if(!StringUtils.isEmpty(user.getBirthday())){
//            Date birth = user.getBirthday();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            birthday = sdf.format(birth);
//        }
//
//        String sex = StringUtils.isEmpty(user.getSex())?null:user.getSex().toString();
//        String email = StringUtils.isEmpty(user.getEmail())?null:user.getEmail();
//        String phone = StringUtils.isEmpty(user.getMobile())?null:user.getMobile();
//        String zu = StringUtils.isEmpty(user_yuanGong.getZu())?null:user_yuanGong.getZu().toString();
//        String gx_id = StringUtils.isEmpty(user_yuanGong.getGongxu().getId())?null:user_yuanGong.getGongxu().getId().toString();
//        String lb_id = StringUtils.isEmpty(user_yuanGong.getLunban().getId())?null:user_yuanGong.getLunban().getId().toString();
//
//        userService.updateUserInfo(xm,birthday,sex,email,phone,user.getId(),zu,gx_id,lb_id,roles,flag);

        return Result.ok("修改成功!",user);
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



}
