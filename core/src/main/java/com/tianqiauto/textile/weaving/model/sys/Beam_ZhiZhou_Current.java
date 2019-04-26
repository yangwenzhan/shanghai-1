package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 织轴
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_zhizhou_current")
@EqualsAndHashCode(exclude = {"zhizhou","heyuehao","status","jitaihao"})
@ToString(exclude = {"zhizhou","heyuehao","status","jitaihao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Beam_ZhiZhou_Current {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name="zhizhou_id")
    private Beam_ZhiZhou zhizhou; //轴


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private Double jingchang;  //经长

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; //状态    机下空/机下满已穿综/机下满未穿综/机下剪轴未穿综/机下剪轴已穿综/布机机上  盘存时多：整经机上、浆纱机上、穿综机上


    private Integer chuanzong_flag;  //是否已经穿综 1 已经穿综 0 未穿综


    private String weizhi; //位置


    @ManyToOne
    @JoinColumn(name = "jitaihao")
    private SheBei jitaihao;  //机台号


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    private String beizhu;   //备注




}
