package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName GongYi_Param
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 11:22
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_param")
@EqualsAndHashCode(exclude = {"leibie"})
@ToString(exclude = {"leibie"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Dict leibie;  //参数类别（分条、分批、浆纱、穿综、织造）

    private String name; //参数名称

    private String danwei; //参数单位

    private Integer sort; //排序号

}
