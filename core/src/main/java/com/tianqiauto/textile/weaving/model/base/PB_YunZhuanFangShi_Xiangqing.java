package com.tianqiauto.textile.weaving.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PB_YunZhuanFangShi
 * @Description 排班—运转方式详情
 * @Author xingxiaoshuai
 * @Date 2019-02-14 16:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_pb_yunzhuanfangshi_xiangqing")
@EqualsAndHashCode(exclude = {"yunZhuanFangShi"})
public class PB_YunZhuanFangShi_Xiangqing {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer sort; //排序号

    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban; //轮班

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci; //班次

    @ManyToOne
    @JoinColumn(name="yunzhuanfangshi_id")
    private PB_YunZhuanFangShi yunZhuanFangShi;



}
