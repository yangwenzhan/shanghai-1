package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
@EqualsAndHashCode(exclude = {"jiHua_buJi","zhizhou","banci","shangzhougong"})
@ToString(exclude = {"jiHua_buJi","zhizhou","banci","shangzhougong"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ZhiXing_BuJi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "jihua_buji_id")
    private JiHua_BuJi jiHua_buJi;


    @ManyToOne
    @JoinColumn(name = "zhizhou_left_id")
    private Beam_ZhiZhou zhiZhou_left;

    @ManyToOne
    @JoinColumn(name = "zhizhou_right_id")
    private Beam_ZhiZhou zhiZhou_right;

    private String beizhu; //上机备注信息
    private Date riqi; //上机日期
    @ManyToOne
    @JoinColumn(name="banci_id")
    private Dict banci; // 上机班次
    private Date shijian; //上机时间
    @ManyToOne
    @JoinColumn(name = "shangzhougong_id")
    private User shangzhougong; //上轴工


    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//结束日期

}
