package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author xingxiaoshuai
 * @Date 2019-04-13 09:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_zhengjing_fenpi")
@EqualsAndHashCode(exclude = {"gongYi","gongYi_paramValues"})
@ToString(exclude = {"gongYi","gongYi_paramValues"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_ZhengJing_FenPi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("gongYi_zhengJing_fenPiSet")
    @ManyToOne
    @JoinColumn(name = "gongyi_id")
    private GongYi gongYi;



    private Integer isCurrent;  //当前使用工艺



    @OneToMany
    @JoinColumn(name = "gongyi_id")
    private Set<GongYi_ParamValue> gongYi_paramValues;






}
