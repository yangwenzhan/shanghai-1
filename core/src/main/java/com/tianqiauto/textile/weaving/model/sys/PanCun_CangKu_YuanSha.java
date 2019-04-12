package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import lombok.*;

import javax.persistence.*;

/**
 * @ClassName PanCun_CangKu_YuanSha
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-28 15:26
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_pancun_cangku_yuansha")
@EqualsAndHashCode(exclude = {"panCunYue","yuanSha"})
@ToString(exclude = {"panCunYue","yuanSha"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PanCun_CangKu_YuanSha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="pancunyue")
    private PanCunYue panCunYue;


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
    private YuanSha yuanSha; //原纱


    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量




}
