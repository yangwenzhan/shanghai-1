package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName GongYi_JingPai
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 10:37
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_jingpai")
@EqualsAndHashCode(exclude = {"yuanSha"})
@ToString(exclude = {"yuanSha"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_JingPai {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
    private YuanSha yuanSha;  //原纱

    private Integer baoshu;  //包数

    private Double yongshaliang;  //用纱量



}
