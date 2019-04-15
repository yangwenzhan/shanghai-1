package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha_ChuKu
 * @Description 原纱出库信息
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"yuanSha_chuKu_shenqing","yuanSha","chukuleixing"})
@ToString(exclude = {"yuanSha_chuKu_shenqing","yuanSha","chukuleixing"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "sys_yuansha_chuku")

//之所以这个表有冗余字段，是为了保证既可以走申请流程，也可以直接进行出库

public class YuanSha_ChuKu {

    /**
     * 原纱出库查询
     *
     * 不需要进行申请，直接出库
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "yuansha_chuku_shenqing_id")
   private YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing; // null


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
   private YuanSha yuanSha; //原纱


    @ManyToOne
    @JoinColumn(name = "chukuleixing_id")
    private Dict chukuleixing; //出库类型

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量


    private String yongtu;   //用途 经纱、纬纱






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
