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
 * @ClassName ChengPin_ZhiLiang
 * @Description 成品测试表
 * @Author xingxiaoshuai
 * @Date 2019-02-27 16:18
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chengpin_zhiliang")
@EqualsAndHashCode(exclude = {"banci","heyuehao","jitaihao"})
@ToString(exclude = {"banci","heyuehao","jitaihao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class ChengPin_ZhiLiang {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date riqi;

    @ManyToOne    //可以为空，因为起机报表上没有此字段
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号



    private String jingmi; //经密
    private String weimi; //纬密
    private String bufu;  //布幅/cm
    private String zhefu; //折幅/cm
    private String mishu; //米数m
    private String zhongliang; //重量kg
    private String kezhong;  //克重


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
