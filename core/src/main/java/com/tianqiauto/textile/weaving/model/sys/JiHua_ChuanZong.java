package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 穿综计划单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jihua_chuanzong")
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao","status","jiHuaChuanZongMain"})
@ToString(exclude = {"banci","jitaihao","heyuehao","status","jiHuaChuanZongMain"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JiHua_ChuanZong {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date riqi; //计划日期

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao;  //机台号

    private Integer deleted; //删除标识

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status;//状态










    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    private String beizhu;   //备注

    @ManyToOne
    @JoinColumn(name = "jihua_chuanzong_main_id")
    @JsonIgnoreProperties("jiHua_chuanZongs")
    private JiHua_ChuanZong_Main jiHuaChuanZongMain;

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date xiadankaishiriqi;//下单开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date xiadanjieshuriqi;//下单结束日期

}
