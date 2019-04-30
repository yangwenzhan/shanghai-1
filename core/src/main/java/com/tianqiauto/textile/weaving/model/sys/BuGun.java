package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName BuGun
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-26 17:15
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_bugun")
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao","luoburen","zhiZhou_left","zhiZhou_right"})
@ToString(exclude = {"banci","jitaihao","heyuehao","luoburen","zhiZhou_left","zhiZhou_right"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuGun {


    /**
     * 通过时间+机台号查询Beam_ZhiZhou_shift对应的轴的信息，每个轴有对应的浆纱质量（也可能没有，整经直接出来织轴）
     * 通过合约号得到原纱信息
     * 通过BuGun_ZhiLiang得到布辊疵点等信息
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date riqi;

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @Column
    private Date luobushijian;//落布时间

    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;

    private String xuhao;   //日期班次

    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao;

    private Double changdu; //落布长度

    private Double shedingchangdu; //设定长度

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "luoburen_id")
    private User luoburen;


    @ManyToOne
    @JoinColumn(name = "zhizhou_left_id")
    private Beam_ZhiZhou zhiZhou_left;

    @ManyToOne
    @JoinColumn(name = "zhizhou_right_id")
    private Beam_ZhiZhou zhiZhou_right;

    @ManyToOne
    @JoinColumn(name = "beam_zhizhou_shift_left_id")
    private Beam_ZhiZhou_Shift beam_zhiZhou_shift_left;

    @ManyToOne
    @JoinColumn(name = "beam_zhizhou_shift_right_id")
    private Beam_ZhiZhou_Shift beam_zhiZhou_shift_right;




    //查询条件
    @Transient
    private Integer kaishixuhao;

    //查询条件
    @Transient
    private Integer jieshuxuhao;




}
