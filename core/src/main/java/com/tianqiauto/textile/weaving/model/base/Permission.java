package com.tianqiauto.textile.weaving.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String permissionName;

    private String permissionCode;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Permission parent_id; //父节点






}
