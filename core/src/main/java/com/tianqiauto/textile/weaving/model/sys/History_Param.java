package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.SheBei;
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
@EqualsAndHashCode(exclude = {"param"})
@ToString(exclude = {"param"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class History_Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date shijian;  //时间

    @ManyToOne
    @JoinColumn(name = "param_id")
    private Param param;

    private String  value;



}
