package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName History_WenShiDu
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-12 19:09
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_history_wenshidu")
@EqualsAndHashCode(exclude = {"wenShiDu"})
@ToString(exclude = {"wenShiDu"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class History_WenShiDu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date shijian;  //时间

    @ManyToOne
    @JoinColumn(name = "wenshidu_id")
    private WenShiDu wenShiDu;  //温湿度名称

    private Double wendu; //温度

    private Double shidu; //湿度


}
