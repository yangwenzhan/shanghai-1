package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName SheBei
 * @Description 设备表
 * @Author xingxiaoshuai
 * @Date 2019-02-14 15:39
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_shebei")
@EqualsAndHashCode(exclude = {"gongxu"})
@ToString(exclude = {"gongxu"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SheBei {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "gongxu_id")
    private  Gongxu gongxu;


    private  String jitaihao; //机台号
    private  String zhizaoshang; //制造商
    private  Integer sort ; //机台序号

    private Integer deleted; //是否删除

    private  Integer jihuatingtai; //计划亭台

    private  String ip;  //ip
    private  String port; //port




}
