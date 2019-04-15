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
@Entity(name = "sys_gongyi_zhengjing_fentiao")
@EqualsAndHashCode(exclude = {"gongYi","gongYi_paramValues","gongYi_zhengJing_fenTiao_paiBanSet"})
@ToString(exclude = {"gongYi","gongYi_paramValues","gongYi_zhengJing_fenTiao_paiBanSet"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_ZhengJing_FenTiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("gongYi_zhengJing_fenTiaoSet")
    @ManyToOne
    @JoinColumn(name = "gongyi_id")
    private GongYi gongYi;



    private Integer isCurrent;  //当前使用工艺



    @OneToMany
    @JoinColumn(name = "gongyi_id")
    private Set<GongYi_ZhengJing_FenTiao_PaiBan> gongYi_zhengJing_fenTiao_paiBanSet; //分条排版  可能为空



    @OneToMany
    @JoinColumn(name = "gongyi_id")
    private Set<GongYi_ParamValue> gongYi_paramValues;






}
