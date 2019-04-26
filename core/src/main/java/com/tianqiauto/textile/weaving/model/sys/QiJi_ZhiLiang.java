package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName QiJi_ZhiLiang
 * @Description 织造起机报表
 * @Author xingxiaoshuai
 * @Date 2019-02-27 16:17
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_qiji_zhiliang")
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao"})
@ToString(exclude = {"banci","jitaihao","heyuehao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class QiJi_ZhiLiang {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date riqi;

    @ManyToOne    //可以为空，因为起机报表上没有此字段
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;



    private String kouhao;  //筘号
    private String zongjing; //总经
    private String jingmi; //经密
    private String weimi; //纬密
    private String koufu; //筘幅/cm
    private String bufu; //布幅/cm
    private String jishangkongzhiweimi;  //机上控制纬密


    private String beizhu; //备注


    @Column
    @CreatedDate
    private Date createTime;


    //查询条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    //查询条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//开始日期




}
