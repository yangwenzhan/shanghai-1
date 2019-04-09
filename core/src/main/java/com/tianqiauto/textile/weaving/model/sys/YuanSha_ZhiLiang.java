package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha
 * @Description 原纱质量
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_zhiliang")
@EqualsAndHashCode(exclude = {"yuanSha"})
@ToString(exclude = {"yuanSha"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YuanSha_ZhiLiang {


    //这是不维护关联关系的一方
    //添加mappedBy属性，则该方不维护关联关系
    //mappedBy的属性为维护关联关系一方的属性名
    @OneToOne(mappedBy="yuanSha_zhiLiang")
    @JsonIgnoreProperties("yuanSha_zhiLiang")
    private YuanSha yuanSha;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private String  shijihaoshu;  //实际号数tex
    private String  kezhonghuichao; //克重回潮
    private String  haoshupianchalv; //号数偏差率%
    private String  danqiang;  //单强cn
    private String  tiaogan; //条干CV%
    private String  xijie; //细节-50%
    private String  cujie; //粗结+50%
    private String  mianjie; //棉节+200%
    private String  meibaoshizhong; //每包实重
    private String  meibaopiancha; //每包偏差
    private String  yingkuizhongliang; //盈亏重量
    private String  chengzhonghuichao; //称重回潮%
    private String  baozhuangdaizhongliang; //包装带重量
    private String  zhiguanzhongliang; //纸管重量
    private String  tongzigeshu; //每包筒子个数










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


}
