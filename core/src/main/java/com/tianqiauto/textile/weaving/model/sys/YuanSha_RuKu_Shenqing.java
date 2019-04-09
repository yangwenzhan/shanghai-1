package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha_ChuKu
 * @Description 原纱入库申请单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_ruku_shenqing")
@EqualsAndHashCode(exclude = {"yuanShaRuKu","yuanSha","laiyuan","status","heyuehao"})
@ToString(exclude = {"yuanShaRuKu","yuanSha","laiyuan","status","heyuehao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YuanSha_RuKu_Shenqing {


    @OneToOne(mappedBy="yuanSha_ruKu_shenqing")
    @JsonIgnoreProperties("yuanSha_ruKu_shenqing")
    private YuanSha_RuKu yuanShaRuKu;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
   private YuanSha yuanSha; //原纱


    @ManyToOne
    @JoinColumn(name="laiyuan_id")
    private Dict laiyuan; //来源     外购/来料加工/车间退库

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量



    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 入库申请状态

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;  //哪个合约号上退的




    private String beizhu; //备注


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//结束日期


}
