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
@Entity(name = "sys_zhixing_jiangsha")
@EqualsAndHashCode(exclude = {"jiHua_jiangSha","zhizhou","banci","jiangshagong"})
@ToString(exclude = {"jiHua_jiangSha","zhizhou","banci","jiangshagong"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ZhiXing_JiangSha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "jihua_jiangsha_id")
    private JiHua_JiangSha jiHua_jiangSha;


    @ManyToOne
    @JoinColumn(name="zhizhou_id")
    private Beam_ZhiZhou zhizhou; //产出织轴信息

    private Double changdu;  //下机实际长度（默认和计划总经长一致）
    private String xiajibeizhu; //下机备注信息
    private Date riqi; //下机日期
    @ManyToOne
    @JoinColumn(name="banci_id")
    private Dict banci; // 下机班次
    private Date shijian; //下机时间
    @ManyToOne
    @JoinColumn(name = "jiangshagong_id")
    private User jiangshagong; //浆纱工




}
