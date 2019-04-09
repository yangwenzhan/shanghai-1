package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Param_LeiBie
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-26 14:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_current")
@EqualsAndHashCode(exclude = {"param","jitaihao"})
@ToString(exclude = {"param","jitaihao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Current {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "param_id")
    private Param param;


    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao; //机台号


    private String value; //参数值

    private Date lastModifyTime;



}
