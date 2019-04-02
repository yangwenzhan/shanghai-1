package com.tianqiauto.textile.weaving.model.result;

import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName ResultUser
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-01 15:55
 * @Version 1.0
 **/

@Data
public class ResultUser {

    private Long id;

    private String username;

    private String gongxu;

    private String lunban;

    private String zu;

    private String roles;

    private String sex;

    private String shifouzaizhi;

    private String email;

    private String mobile;

    private Date birthday;


     public static Page<ResultUser> convert(Page<User> userPage) {
        Page<ResultUser> dtoPage = userPage.map(user -> {
            ResultUser dto = new ResultUser();
            MyCopyProperties.copyProperties(user, dto, Arrays.asList("id", "zu","email","mobile","birthday"));

            dto.setUsername(user.getUsername() + " " + user.getXingming());
            if(user.getGongxu()!=null){dto.setGongxu(user.getGongxu().getName());}
            if(user.getLunban()!=null){dto.setLunban(user.getLunban().getName());}
            if(user.getRoles().size()>0){
                String roles = "";
                for (Role role:user.getRoles()) {
                    roles= roles+role.getName()+"、";
                }
                dto.setRoles(roles.substring(0,roles.length()-1));
            }
            dto.setSex(user.getSex()==1?"男":"女");
            dto.setShifouzaizhi(user.getShifouzaizhi()==1?"在职":"离职");

            return dto;
        });
        return dtoPage;
    }






}
