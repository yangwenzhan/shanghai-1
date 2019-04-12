package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 整经计划单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jihua_zhengjing_main")
@EqualsAndHashCode(exclude = {"jiHua_jiangSha_main","banci","heyuehao","status"})
@ToString(exclude = {"jiHua_jiangSha_main","banci","heyuehao","status"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JiHua_ZhengJing_Main {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "jiHua_jiangSha_main_id")
    private JiHua_JiangSha_Main jiHua_jiangSha_main;


    private Date riqi; //计划日期

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status;//状态


    private String beizhu;   //备注

    private Integer zongjingchang; //总经长
    private Integer zongjinggenshu; //总经根数
    private Integer zongjingzhoushu; //轴数(不需要浆纱默认1)
    private Integer danzhoutoufen; //单轴头份（自动计算出来填充，不需要浆纱的即为总经根数）



    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;




}
