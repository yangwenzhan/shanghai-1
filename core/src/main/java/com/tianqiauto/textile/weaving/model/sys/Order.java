package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Order
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_order")
@EqualsAndHashCode(exclude = {"heyuehaos"})
public class Order {
    /**
     * 新增合约号（新增、换经纱、换纬纱）
     *
     * 新增订单
     * 修改订单
     * 撤销订单
     *
     * 查询订单（订单进度）
     *
     * 合约号查询
     *
     * 坯布规格=入库规格=【幅宽/经密/纬密/经纱成分/经纱支数/纬纱支数/纬纱支数/特殊要求】拼接而成
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String dingdanhao; //订单号

    @JsonIgnoreProperties("order")
    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Heyuehao> heyuehaos; //合约号

    @Column(precision = 12,scale = 2)
    private Double yiwanchengliang; //已完成量（默认为0，有成品入库动作时更新对应订单已完成量）


    private String pibuguige; //坯布规格

    private String rukuguige; //入库规格

    private String gongzhiguige; //公制规格

    private Date xiadanriqi; //下单日期

//    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jiaohuoriqi; //交货日期

    private Integer xiadanshuliang; // 下单数量

    private String baozhuangyaoqiu; //包装要求

    private String baozhuangmaitou; //包装唛头

    @Column(precision = 12,scale = 2)
    private Double jingbaimiyongsha; // 经百米用纱量

    @Column(precision = 12,scale = 2)
    private Double weibaimiyongsha; //纬百米用纱量

    private Integer pingfangkezhong; //平方克重

    @ManyToOne
    @JoinColumn(name = "yuanliaoleixing_id")
    private Dict yuanliaoleixing; //原料类型

    @ManyToOne
    @JoinColumn(name = "chengpinyongtu_id")
    private Dict chengpinyongtu;  //成品用途

    private String teshuyueding; //特殊约定


    private String erpshangpindaima; //erp商品代码


    @ManyToOne
    @JoinColumn(name = "yingxiaoyuan_id")
    private User yingxiaoyuan; //营销员

    @ManyToOne
    @JoinColumn(name="jingli_id")
    private User jingli; //经理

    @ManyToOne
    @JoinColumn(name = "kehuxinxi_id")
    private Dict kehuxinxi; //客户信息

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; //订单状态 10 15 20 30 40   已下单/生产中/已完成

    //这5个字段根据坯布规格拆分出来
    private Double fukuan;            //幅宽
    private Double jingmi;            //经密
    private Double weimi;             //纬密
    private String jingshachengfen; //经纱成分
    private String jingshazhishu;   //经纱支数
    private String weishachengfen;  //经纱成分
    private String weishazhishu;    //纬纱支数
    private String teshuyaoqiu;     //特殊要求

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

    private String beizhu;   //备注

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date xiadankaishiriqi;//下单开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date xiadanjieshuriqi;//下单结束日期

}
