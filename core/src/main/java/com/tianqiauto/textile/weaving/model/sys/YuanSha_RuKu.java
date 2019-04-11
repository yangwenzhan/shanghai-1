package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha
 * @Description 原纱入库信息
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_ruku")
@EqualsAndHashCode(exclude = {"yuanSha","yuanSha_ruKu_shenqing","lingyongren","laiyuan"})
@ToString(exclude = {"yuanSha","yuanSha_ruKu_shenqing","lingyongren","laiyuan"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YuanSha_RuKu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "yuansha_id")
    private YuanSha yuanSha;

    @OneToOne
    @JoinColumn(name = "yuansha_ruku_shenqing_id")
    @JsonIgnoreProperties("yuanShaRuKu")
    private YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing;


    private Date goururiqi; //购入日期

    @ManyToOne
    @JoinColumn(name="laiyuan_id")
    private Dict laiyuan; //来源     外购/来料加工/车间退库

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量

    @ManyToOne
    @JoinColumn(name="lingyongren_id")
    private User lingyongren;//领用人






    private String beizhu; //备注
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

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//结束日期

}
