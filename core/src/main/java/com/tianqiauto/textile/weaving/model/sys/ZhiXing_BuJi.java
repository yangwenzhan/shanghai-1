package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName ZhiXing_Zhengjing
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-25 16:33
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_zhixing_buji")
public class ZhiXing_BuJi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "jihua_buji_id")
    private JiHua_BuJi jiHua_buJi;


    @ManyToOne
    @JoinColumn(name="zhizhou_id")
    private Beam_ZhiZhou zhizhou; //织轴信息

    private String beizhu; //上机备注信息
    private Date riqi; //上机日期
    @ManyToOne
    @JoinColumn(name="banci_id")
    private Dict banci; // 上机班次
    private Date shijian; //上机时间
    @ManyToOne
    @JoinColumn(name = "shangzhougong_id")
    private User shangzhougong; //上轴工




}
