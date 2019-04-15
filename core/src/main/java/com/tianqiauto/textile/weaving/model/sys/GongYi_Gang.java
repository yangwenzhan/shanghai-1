package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName GongYi_Gang
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 10:53
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_gang")
@EqualsAndHashCode(exclude = {"gongYi"})
@ToString(exclude = {"gongYi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_Gang {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnoreProperties("gongYi_gangSet")
    @ManyToOne
    @JoinColumn(name = "gongyi_id")
    private GongYi gongYi;


    private Integer ganghao; //缸号

    private Integer shangpishu; //上批数（挂纱架子上上几批）

    private Integer zhoushu; //轴数
    private Integer toufen; //头份    轴数*头份必须等于总经根数

    private Integer jingchang; //经长

}
