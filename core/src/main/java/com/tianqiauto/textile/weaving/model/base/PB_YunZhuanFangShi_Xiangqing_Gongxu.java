package com.tianqiauto.textile.weaving.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PB_YunZhuanFangShi
 * @Description 排班—运转方式详情_工序
 * @Author xingxiaoshuai
 * @Date 2019-02-14 16:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_pb_yunzhuanfangshi_xiangqing_gongxu")
public class PB_YunZhuanFangShi_Xiangqing_Gongxu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "xiangqing_id")
    private PB_YunZhuanFangShi_Xiangqing pb_yunZhuanFangShi_xiangqing;

    @OneToOne
    @JoinColumn(name = "gongxu_id")
    private Gongxu gongxu;

}
