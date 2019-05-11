package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
@Entity(name = "sys_history_param")
@EqualsAndHashCode(exclude = {"param","jitaihao"})
@ToString(exclude = {"param","jitaihao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class History_Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Date shijian;  //时间

    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao;

    @ManyToOne
    @JoinColumn(name = "param_id")
    private Param param;

    private String  value;

    @Column
    @CreatedDate
    private Date createTime;



}
