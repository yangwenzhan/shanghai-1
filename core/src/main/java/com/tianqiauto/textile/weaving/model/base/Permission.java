package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-27 20:27
 * @Version 1.0
 **/
@Entity(name = "base_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles"})
@ToString(exclude = {"roles"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String permissionName;

    private String permissionCode;


    private Long parentId; //父节点


    @JsonIgnoreProperties("permissions")
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;


}
