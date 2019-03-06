package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
     *
     * 合约号查询
     *
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String dingdanhao; //订单号

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Heyuehao> heyuehaos; //合约号

    @Column(precision = 12,scale = 2)
    private Double yiwanchengliang; //已完成量（默认为0，有成品入库动作时更新对应订单已完成量）


    private String pibuguige; //坯布规格

    private String rukuguige; //入库规格

    private String gongzhiguige; //公制规格

    private Date xiadanriqi; //下单日期

    private Date jiaohuoriqi; //交货日期

    private Integer xiadanshuliang; // 下单数量

    private String baozhuangyaoqiu; //包装要求

    private String baozhuangmaitou; //包装唛头

    @Column(precision = 12,scale = 2)
    private Double jingbaimiyongsha; // 经百米用纱量

    @Column(precision = 12,scale = 2)
    private Double weibaimiyongsha; //纬百米用纱量

    private Integer pingfangkezhong; //平方克重

    @Column(precision = 12,scale = 2)
    private Double jingxiangjindu; //经向紧度

    @Column(precision = 12,scale = 2)
    private Double weixiangjindu; //纬向紧度

    @Column(precision = 12,scale = 2)
    private Double zongjindu;  //总紧度

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
    private Double fukuan;  //幅宽
    private String jingshapinzhong; //经纱品种
    private String weishapinzhong; //纬纱品种
    private Double jingmi; //经密
    private Double weimi; //纬密






    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;



    private String beizhu;   //备注




}
