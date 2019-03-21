package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
@EqualsAndHashCode(exclude = {"orders","yuansha_jingsha","yuansha_weisha"})
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

    @JsonIgnoreProperties("heyuehao_jingsha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_jingsha",joinColumns = @JoinColumn(name = "heyuehao_id"),
            inverseJoinColumns = @JoinColumn(name = "jingsha_id"))
    private Set<Heyuehao_YuanSha> jingsha; //经纱

    @JsonIgnoreProperties("heyuehao_weisha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_weisha",joinColumns = @JoinColumn(name = "heyuehao_id"),
            inverseJoinColumns = @JoinColumn(name = "weisha_id"))
    private Set<Heyuehao_YuanSha> weisha; //纬纱






    private String beizhu; //备注


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;






}
