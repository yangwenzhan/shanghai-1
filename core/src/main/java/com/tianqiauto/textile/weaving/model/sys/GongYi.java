package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-13 09:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi")
@EqualsAndHashCode(exclude = {"jingPaiSet","weiPaiSet","gongYi_gangSet","gongYi_zhengJing_fenTiao_paiBanSet","heyuehao","gongYi_zhiZao","gongYi_paramValues"})
@ToString(exclude = {"jingPaiSet","weiPaiSet","gongYi_gangSet","gongYi_zhengJing_fenTiao_paiBanSet","heyuehao","gongYi_zhiZao","gongYi_paramValues"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String gongyibianhao;   //工艺编号：用户自己编号，方便对不同类型工艺进行筛选


    private Integer dijing; //地经
    private Integer bianjing; //边经




    private String fentiao_fenpi_flag; //值为分条或分批



    @OneToMany
    @JoinColumn(name="yongyi_id")
    private Set<GongYi_JingPai> jingPaiSet;  //经排
    private  String jingpai_paihua;  //经排排花

    @OneToMany
    @JoinColumn(name = "gongyi_id")
    private Set<GongYi_WeiPai> weiPaiSet;  //纬排
    private  String weipai_paihua;  //纬排排花

    @JsonIgnoreProperties("gongYi")
    @OneToMany(mappedBy = "gongYi")
    private Set<GongYi_Gang> gongYi_gangSet;  //缸   可能为空




    @JsonIgnoreProperties("gongYi")
    @OneToOne(mappedBy = "gongYi")
    private Heyuehao heyuehao;
//==========================================================================================================


    @JsonIgnoreProperties("gongYi")
    @OneToMany
    @JoinColumn
    private Set<GongYi_ZhengJing_FenPi> gongYi_zhengJing_fenPiSet;

    @JsonIgnoreProperties("gongYi")
    @OneToMany(mappedBy = "gongYi")
    private Set<GongYi_ZhengJing_FenTiao> gongYi_zhengJing_fenTiaoSet;


    @JsonIgnoreProperties("gongYi")
    @OneToMany(mappedBy = "gongYi")
    private Set<GongYi_JiangSha> gongYi_jiangShaSet;

    @JsonIgnoreProperties("gongYi")
    @OneToMany(mappedBy = "gongYi")
    private Set<GongYi_ChuanZong> gongYi_chuanZongSet;



    @JsonIgnoreProperties("gongYi")
    @OneToMany(mappedBy = "gongYi")
    private Set<GongYi_ZhiZao> gongYi_zhiZaoSet;







    @CreatedDate
    private Date createDate;
    @CreatedBy
    private String createdBy;
}
