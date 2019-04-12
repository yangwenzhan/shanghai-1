package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName ZhiXing_Zhengjing
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-25 16:33
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_zhixing_zhengjing")
@EqualsAndHashCode(exclude = {"jiHua_zhengJing","jingzhou","zhizhou","banci","zhengjinggong"})
@ToString(exclude = {"jiHua_zhengJing","jingzhou","zhizhou","banci","zhengjinggong"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class ZhiXing_Zhengjing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "jihua_zhengjing_id")
    private JiHua_ZhengJing jiHua_zhengJing;



    //经轴下机详情表、织轴下机详情表

    //    //整经下机相关动作：1。考虑跨班产量计算；2。整经计划状态发生改变；3。登记的经轴/织轴状态发生改变。4。如果浆纱计划不为空，浆纱计划状态需要发生改变
    @ManyToOne
    @JoinColumn(name = "jingzhou_id")
    private Beam_JingZhou jingzhou; //产出经轴信息

    @ManyToOne
    @JoinColumn(name = "zhizhou_id")
    private Beam_ZhiZhou zhizhou; //产出织轴信息

    private Integer duantoushu; //断头数
    private Double changdu;  //下机实际长度（默认和计划总经长一致）
    private String beizhu; //下机备注信息
    private Date xiajiriqi; //下机日期
    @ManyToOne
    @JoinColumn(name="banci_id")
    private Dict banci; // 下机班次
    private Date xiajishijian; //下机时间
    @ManyToOne
    @JoinColumn(name = "zhengjinggong_id")
    private User zhengjinggong; //整经工



}
