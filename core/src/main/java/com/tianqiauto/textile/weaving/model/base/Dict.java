package com.tianqiauto.textile.weaving.model.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_dict")
/**
 *  数据字典表
 */
public class Dict {

    /**
     * 订单原料类型（原纱表来源字段同样使用）
     * 订单成品用途
     * 订单客户信息
     * 订单状态
     * 原纱供应商
     * 原纱包装形式
     * 原纱出库类型
     *
     *
     * 轮班
     * 班次
     *
     * 浆纱计划状态
     * 整经计划状态
     * 布机计划状态
     *
     * 布机该车类型
     *
     * 经轴状态
     *
     * 织轴状态
     *
     * 成品入/出库类型
     *
     */



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;
    private Integer sort;  //排序号





//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "type_id")
//    private Dict_Type type;






}
