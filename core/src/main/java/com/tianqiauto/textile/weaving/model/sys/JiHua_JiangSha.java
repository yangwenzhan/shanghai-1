package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Order
 * @Description 浆纱计划单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jihua_jiangsha")
@EqualsAndHashCode(exclude = {"jiHua_jiangSha_zhiZhouSet","jiHua_jiangSha_main","banci","heyuehao","jitaihao","status"})
@ToString(exclude = {"jiHua_jiangSha_zhiZhouSet","jiHua_jiangSha_main","banci","heyuehao","jitaihao","status"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JiHua_JiangSha {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "jiHua_jiangSha_main_id")
    private JiHua_JiangSha_Main jiHua_jiangSha_main;


    //输入：日期、班次、合约号、机台号、缸号、优先级

    private Date riqi; //计划浆纱日期

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号

    private String ganghao; //浆纱缸号

    private Integer Youxianji; //优先级

    private Integer zhoukuan;  //轴宽




    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status;//状态  计划已下达/整经生产中/整经已完成/浆纱生产中/浆纱已完成


    @JsonIgnoreProperties("JiHuaJiangShas")
    @OneToMany
    @JoinColumn(name = "jiangshajihua_id")
    private Set<JiHua_JiangSha_ZhiZhou> jiHua_jiangSha_zhiZhouSet; //合约号

    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    private String beizhu;   //备注


}
