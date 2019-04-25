package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Heyuehao
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:26
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_heyuehao")
@EqualsAndHashCode(exclude = {"order","jingsha","weisha","gongYi"})
@ToString(exclude = {"order","jingsha","weisha","gongYi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Heyuehao {
    /**
     *
     * 合约号查询（订单号、坯布规格、合约号、经纱品种、纬纱品种、备注）
     *
     * 合约号修改（根据原纱对应合约号领用情况、考虑经纬纱）
     *
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("heyuehaos")
    private Order order; //订单


    private String name; //合约号

    private String kehubianhaomiaoshu;//客户编号描述

    @JsonIgnoreProperties("jingsha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_jingsha",joinColumns = @JoinColumn(name = "heyuehao_id"),
            inverseJoinColumns = @JoinColumn(name = "jingsha_id"))
    private Set<Heyuehao_YuanSha> jingsha; //经纱

    @JsonIgnoreProperties("weisha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_weisha",joinColumns = @JoinColumn(name = "heyuehao_id"),
            inverseJoinColumns = @JoinColumn(name = "weisha_id"))
    private Set<Heyuehao_YuanSha> weisha; //纬纱

    private String beizhu; //备注


    @JsonIgnoreProperties("heyuehao")
    @OneToOne
    @JoinColumn(name = "gongyi_id")
    private GongYi gongYi;

    @Column(precision = 18, scale = 4)
    private BigDecimal zhisuo;//织缩


    @Column
    @CreatedDate
    private Date createTime;
    @Column
    @CreatedBy
    private String  luruRen;
    @Column
    @LastModifiedDate
    private Date lastModifyTime;
    @Column
    @LastModifiedBy
    private String lastModifyRen;

}
