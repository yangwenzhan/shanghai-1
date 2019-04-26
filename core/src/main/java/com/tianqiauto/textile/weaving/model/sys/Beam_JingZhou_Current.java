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
 * @Description 经轴实时数据
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_jingzhou_current")
@EqualsAndHashCode(exclude = {"jitaihao","status","jingZhou","heyuehao"})
@ToString(exclude = {"jitaihao","status","jingZhou","heyuehao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Beam_JingZhou_Current {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="jingzhou_id")
    private Beam_JingZhou jingZhou; //轴

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private Double jingchang;  //经长


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; //状态    机下空/机下满/浆纱机上     盘存时多：整经机上、浆纱机上

    private String beizhu;   //备注

    @ManyToOne
    @JoinColumn(name = "jitaihao")
    private SheBei jitaihao;  //机台号


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;





}
