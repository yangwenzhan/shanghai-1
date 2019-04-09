package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

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
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao","luoburen","zhiZhou"})
@ToString(exclude = {"banci","jitaihao","heyuehao","luoburen","zhiZhou"})
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


    private Date riqi;

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    private Date luobushijian;

    @ManyToOne
    @JoinColumn(name = "jitai_id")     //预计换轴时间： 查询当前机台轴的上机时间，查询出时间大于上机时间的落布信息+当前机台布辊长度，预测轴剩余经长。
    private SheBei jitaihao;

    private Double changdu; //落布长度

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "luoburen_id")
    private User luoburen;


    @ManyToOne
    @JoinColumn(name = "zhizhou_id")
    private Beam_ZhiZhou zhiZhou;



}
