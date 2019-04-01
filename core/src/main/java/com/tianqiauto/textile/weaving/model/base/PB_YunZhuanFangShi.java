package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName PB_YunZhuanFangShi
 * @Description 排班—运转方式
 * @Author xingxiaoshuai
 * @Date 2019-02-14 16:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_pb_yunzhuanfangshi")
@EqualsAndHashCode(exclude = {"yunZhuanFangShi_xiangqingSet"})
public class PB_YunZhuanFangShi {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name; //运转方式名称

    private Integer paibanshu; //每天有几个班

    private Integer lunbanshu; //轮班数

    @JsonIgnoreProperties("yunZhuanFangShi")
    @OneToMany
    @JoinColumn(name="yunzhuanfangshi_id")
    private Set<PB_YunZhuanFangShi_Xiangqing> yunZhuanFangShi_xiangqingSet;



}
