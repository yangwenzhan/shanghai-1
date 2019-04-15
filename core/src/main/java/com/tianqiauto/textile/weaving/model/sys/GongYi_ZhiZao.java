package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName GongYi_Zhengjing_FenTiao
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 09:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_zhizao")
@EqualsAndHashCode(exclude = {"gongYi"})
@ToString(exclude = {"gongYi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_ZhiZao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("gongYi_zhiZao")
    @OneToOne(mappedBy = "gongYi_zhiZao")
    private GongYi gongYi;



    private String jishangweimi;  //机上纬密

    private Double luobuchangdu;  //落布长度



    //这三个从订单表同步过来，可以让用户进行修改
    private String pibuyaoqiu; //坯布要求
    private String chengbaoyaoqiu;  //成包要求
    private String baozhuangmaitou; //包装唛头






}
