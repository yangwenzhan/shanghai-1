package com.tianqiauto.textile.weaving.controller;

import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.RoleRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * 重构或修改代码查看是否影响模块相关其他功能的时候，可以让测试用例跑一下，看哪些变成了未通过。
 *
 * 日期向前台传的时候传时间戳。
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserController2Test {


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Autowired
    private UserRepository userRepository;

//    @Test
    public void testJpaPersist(){



    }


//    @Test
    public void findAllTest() throws Exception {


        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/findAll")
//      .param("","");
        .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }


//    @Test
    public void saveTest() throws Exception {
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        String content = "{\"username\":\"xxs\",\"password\":null,\"birthday\":"+date.getTime()+"}";

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }




    @Autowired
    private RoleRepository roleRepository;

//    @Test
    public void addUserAndRole(){

        Role role1 = new Role();
        Role role2 = new Role();
        role1.setName("RR1");
        role2.setName("RR2");

        roleRepository.save(role1);
        roleRepository.save(role2);


        User user = new User();
        user.setUsername("U1");
        user.setPassword("U1");

        HashSet roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        user.setRoles(roles);



        userRepository.save(user);


    }



}
