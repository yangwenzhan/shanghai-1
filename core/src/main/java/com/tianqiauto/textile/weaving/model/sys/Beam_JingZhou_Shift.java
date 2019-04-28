package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 经轴归档数据
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_jingzhou_shift")
@EqualsAndHashCode(exclude = {"jingZhou","heyuehao","jitaihao_zhengjing","jitaihao_jiangsha"})
@ToString(exclude = {"jingZhou","heyuehao","jitaihao_zhengjing","jitaihao_jiangsha"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Beam_JingZhou_Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="jingzhou_id")
    private Beam_JingZhou jingZhou; //轴

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private String ganghao;  // 缸号

    private Double jingchang;  //经长


    @ManyToOne
    @JoinColumn(name = "jth_zhengjing")
    private SheBei jitaihao_zhengjing;  //机台号
    private Date zhengjing_time; //整经下机时间




    private String beizhu;   //备注

    @OneToOne
    @JoinColumn(name = "zhixing_id")
    private ZhiXing_Zhengjing zhiXing_zhengjing;//整经执行记录




}
