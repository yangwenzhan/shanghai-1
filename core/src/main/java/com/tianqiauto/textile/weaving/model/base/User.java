package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Entity(name = "base_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles","user_yuanGong"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private  String xingming;

    private Date birthday;

    @Email(message = "不是有效的邮箱地址")
    private String email;

    private String mobile;

    //性别
    private Integer sex;

    //是否在职
    private Integer shifouzaizhi;

    //一对一关系
    @JsonIgnoreProperties("user")
    @OneToOne
    @JoinColumn(name = "user_yuangong_id")
    private User_YuanGong user_yuanGong;

    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "base_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @Transient
    private String ghxm;


//        @Transient          //不映射生成数据库对应字段
//    private Integer ageFrom;
//    @Transient
//    private Integer ageTo;



    public void setGhxm(String ghxm){
        this.ghxm=username+" "+xingming;
    }








}
