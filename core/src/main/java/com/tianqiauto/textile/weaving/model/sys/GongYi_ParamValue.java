package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName GongYi_ParamValue
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 11:32
 * @Version 1.0
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_paramvalue")
@EqualsAndHashCode(exclude = {"param"})
@ToString(exclude = {"param"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_ParamValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "param_id")
    private GongYi_Param param; //工艺参数

    private  String value; //工艺参数值



}
