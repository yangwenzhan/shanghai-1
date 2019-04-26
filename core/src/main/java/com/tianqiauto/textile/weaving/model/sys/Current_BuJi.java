package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Current_BuJi
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-12 14:49
 * @Version 1.0
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_current_buji")
@EqualsAndHashCode(exclude = {"jitaihao","qitingzhuangtai","yunxingzhuangtai","heyuehao","dangchegong","zhiZhou_left","zhiZhou_right"})
@ToString(exclude = {"jitaihao","qitingzhuangtai","yunxingzhuangtai","heyuehao","dangchegong","zhiZhou_left","zhiZhou_right"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Current_BuJi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao; //机台号

    private Integer onlineflag;  //1为在线 0为离线

    @ManyToOne
    @JoinColumn(name = "qitingzhuangtai_id")
    private Dict qitingzhuangtai; //运行状态：运行、停止

    @ManyToOne
    @JoinColumn(name = "yunxingzhuangtai_id")
    private Dict yunxingzhuangtai;      //采集过来的实际运行状态


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;  //合约号

    private Double chesu; //车速

    private Double daweicishu; //当班打纬次数

    private Double dangbanbuchang; //当班布长

    private Double buchang; //当前布辊布长
    private Double shedingbuchang; //设定布长

    private Integer luobushijian; //预计落布时间
    private Integer liaojishijian;  //预计了机时间




    private Integer jingting;//当班经停次数
    private Integer weiting;//当班纬停次数
    private Integer zongting;//当班总停次数

    private Integer yunxingshijian; //运行时间

    private Integer jingtingshijian; //当班经停时间（秒）
    private Integer weitingshijian; //当班纬停时间（秒）
    private Integer zongtingshijian; //当班总停时间（秒）

    private Integer shiwanweiweiting; //十万纬纬停次数   setter


    private Double weimi; //纬密 根/inch



    private Double suolv; //缩率


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User dangchegong;  //挡车工


    private Double xiaolv;//当班效率   //结合当班运行时间，当班开始时间

    @ManyToOne
    @JoinColumn(name = "zhizhou_left_id")
    private Beam_ZhiZhou zhiZhou_left;

    @ManyToOne
    @JoinColumn(name = "zhizhou_right_id")
    private Beam_ZhiZhou zhiZhou_right;


    private Date lastModifyTime;


}
