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
    private String gongxu_id;

    private String lunban;
    private String lunban_id;

    private String zu;

    private String roles;
    private String roles_id;

    private String sex;
    private String sex_id;

    private String shifouzaizhi;

    private String email;

    private String mobile;

    private Date birthday;


     public static Page<ResultUser> convert(Page<User> userPage) {
        Page<ResultUser> dtoPage = userPage.map(user -> {
            ResultUser dto = new ResultUser();
            MyCopyProperties.copyProperties(user, dto, Arrays.asList("id", "zu","email","mobile","birthday"));

            dto.setUsername(user.getUsername() + " " + user.getXingming());
            if(user.getGongxu()!=null){dto.setGongxu(user.getGongxu().getName());dto.setGongxu_id(user.getGongxu().getId().toString());}
            if(user.getLunban()!=null){dto.setLunban(user.getLunban().getName());dto.setLunban_id(user.getLunban().getId().toString());}
            if(user.getRoles().size()>0){
                String roles = "";String roles_id = "";
                for (Role role:user.getRoles()) {
                    roles= roles+role.getName()+"、";
                    roles_id = roles_id+role.getId()+",";
                }
                dto.setRoles(roles.substring(0,roles.length()-1));
                dto.setRoles_id(roles_id.substring(0,roles_id.length()-1));
            }
            dto.setSex((user.getSex()!=null && user.getSex()==1)?"男":"女");
            dto.setSex_id(user.getSex()!=null?user.getSex().toString():null);
            dto.setShifouzaizhi((user.getShifouzaizhi()!=null && user.getShifouzaizhi()==1)?"在职":"离职");

            return dto;
        });
        return dtoPage;
    }






}
