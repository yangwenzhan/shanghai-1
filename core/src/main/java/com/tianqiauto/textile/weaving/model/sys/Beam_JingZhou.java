package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName Order
 * @Description 经轴
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_jingzhou")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Beam_JingZhou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zhouhao; //轴号

    @Column(scale = 2)
    private Double zhoukuan; //轴宽

    private String beizhu;   //备注





}
