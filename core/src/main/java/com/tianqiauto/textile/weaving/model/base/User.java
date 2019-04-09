package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Entity(name = "base_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles","gongxu","lunban"})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"roles","gongxu","lunban"})
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

    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "base_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;





    //关联工序表 可为空
    @ManyToOne
    @JoinColumn(name = "gongxu_id")
    private Gongxu gongxu;

    //若工序为空，则组必为空，新增时不需展示组
    private Integer zu;

    //所属轮班
    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;




    @Transient
    private String ghxm;


//        @Transient          //不映射生成数据库对应字段
//    private Integer ageFrom;
//    @Transient
//    private Integer ageTo;



    public void setGhxm(String ghxm){
        this.ghxm=username+" "+xingming;
    }






    @Column
    @CreatedDate
    private Date createDate;
    @Column
    @LastModifiedDate
    private Date lastModifiedDate;
    @Column
    @CreatedBy
    private String createdBy;
    @Column
    @LastModifiedBy
    private String modifiedBy;
    @Column
    @Version
    private Long version;


}
