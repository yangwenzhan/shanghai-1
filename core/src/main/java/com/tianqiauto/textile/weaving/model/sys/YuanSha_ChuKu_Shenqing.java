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
 * @Description 原纱出库申请单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_chuku_shenqing")
@EqualsAndHashCode(exclude = {"yuanSha","chukuleixing","status","cangkuquerenren","heyuehao"})
@ToString(exclude = {"yuanSha","chukuleixing","status","cangkuquerenren","heyuehao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YuanSha_ChuKu_Shenqing {

    /**
     *
     *  原纱出库申请查询
     *
     *  原纱出库申请新增、修改（确认之前可以修改）、删除（确认之前可以删除）
     *
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
   @JoinColumn(name = "yuansha_id")
   private YuanSha yuanSha; //原纱

    @OneToOne(mappedBy="yuanSha_chuKu_shenqing")
    @JsonIgnoreProperties("yuanSha_chuKu_shenqing")
    private YuanSha_ChuKu yuanShaChuKu;


    private Date yaoqiulingyongshijian; //要求领用时间


    @ManyToOne
    @JoinColumn(name = "chukuleixing_id")
    private Dict chukuleixing; //出库类型

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 出库申请状态

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
