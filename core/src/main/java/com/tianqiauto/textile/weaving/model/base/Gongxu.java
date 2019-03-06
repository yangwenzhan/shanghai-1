package com.tianqiauto.textile.weaving.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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
public class Gongxu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  //工序名称

    private Integer sort; //排序号


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Gongxu parent_gongxu; //父工序




}
