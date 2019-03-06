package com.tianqiauto.textile.weaving.controller;


import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import com.tianqiauto.textile.weaving.util.log.Logger;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userJpaRepository;

    @GetMapping("findAll")
    @Logger
    @ApiOperation(value = "用户查询服务")
    @PreAuthorize("hasAuthority('USER_VIEW')")
//    @PreAuthorize("hasAnyAuthority('ROLE_RR1')")
    public List<User> findAll(@PageableDefault(size = 15,page = 0,sort = "username,asc") Pageable pageable, Principal principal){

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());

        System.out.println("当前用户名："+principal.getName());

        List<User> users =  userJpaRepository.findAll();

        return users;
    }

    @GetMapping("{name}")
    @Logger(msg = "根据姓名查询员工信息")
    @ApiOperation(value = "根据姓名进行用户查询")
    public User findByName(@PathVariable(name = "name") @ApiParam("用户姓名") String username){
         userJpaRepository.findByUsername(username);

         return new User();
    }



    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "用户新增")
    public Result save(@Valid @RequestBody User user){
        String pwd = user.getPassword();
        String encryptPwd = passwordEncoder.encode(pwd);
        user.setPassword(encryptPwd);
        userJpaRepository.save(user);
        return Result.ok("新增用户成功",user);
    }





}
