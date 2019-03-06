package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 织轴归档数据
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_zhizhou_shift")
public class Beam_ZhiZhou_Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "zhizhou_id")
    private Beam_JingZhou zhizhou; //轴


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private Double jingchang;  //经长


    @ManyToOne
    @JoinColumn(name = "jth_zhengjing")
    private SheBei jitaihao_zhengjing;  //机台号

    private Date zhengjing_time; //整经下机时间


    @ManyToOne
    @JoinColumn(name = "jth_jiangsha_shangche")
    private SheBei jitaihao_jiangsha_shangche;  //机台号

    private Date jiangsha_time_shangche; //浆纱上机时间

    @ManyToOne
    @JoinColumn(name = "jth_jiangsha_xiache")
    private SheBei jitaihao_jiangsha_xiache;  //机台号

    private Date jiangsha_time_xiache; //浆纱下机时间

    @ManyToOne
    @JoinColumn(name = "jth_chuanzong")
    private SheBei jitaihao_chuanzong;  //机台号

    private Date chuanzong_time; //穿综下机时间

    @ManyToOne
    @JoinColumn(name = "jth_buji")
    private SheBei jitaihao_buji;  //机台号

    private Date buji_time; //布机上机时间

    private Date buji_xiaji_time; //布机下机时间







    private String beizhu;   //备注




}
