package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName SheBei
 * @Description 工序表
 * @Author xingxiaoshuai
 * @Date 2019-02-14 15:39
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_gongxu")
@EqualsAndHashCode(exclude = {"parentGongxu"})
@ToString(exclude = {"parentGongxu"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gongxu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  //工序名称

    private Integer sort; //排序号


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Gongxu parentGongxu; //父工序




}
