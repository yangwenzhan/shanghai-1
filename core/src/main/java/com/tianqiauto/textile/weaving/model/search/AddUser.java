package com.tianqiauto.textile.weaving.model.search;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.result.ResultUser;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName AddUser
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-09 14:16
 * @Version 1.0
 **/

@Data
public class AddUser {


    private Date birthday;
    private String email;
    private String gongxu;
    private String lunban;
    private String mobile;
    private String role;
    private String sex;
    private String shifouzaizhi;
    private String username;
    private String xingming;
    private String zu;




    public  User buildUser(){
        User user = new User();

        return user;
    }

}
