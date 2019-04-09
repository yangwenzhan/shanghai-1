package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName Param_LeiBie
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-26 14:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_param")
@EqualsAndHashCode(exclude = {"leiBie"})
@ToString(exclude = {"leiBie"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leibie_id")
    private Param_LeiBie leiBie;

    private String name;  //参数名称


    private String danwei; //参数单位


    private Integer baojing_flag; //报警flag 1为报警 0为不需要报警

    private Integer zhanshi_flag; //展示flag 1为展示 0为不展示

    private Integer cunchu_flag; //存储flag 1为需要存储 0为不需要存储

    private Integer cunchuzhouqi; //存储周期  单位为秒

    private Integer cunchushichang; //存储时长 单位为小时




    private Integer xuhao;  //参数类别展示顺序


}
