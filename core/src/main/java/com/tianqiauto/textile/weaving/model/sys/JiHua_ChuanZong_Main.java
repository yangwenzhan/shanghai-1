package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
@Entity(name = "sys_jihua_chuanzong_main")
@EqualsAndHashCode(exclude = {"banci","heyuehao","status","jiHua_chuanZongs","youxianji"})
@ToString(exclude = {"banci","heyuehao","status","jiHua_chuanZongs","youxianji"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JiHua_ChuanZong_Main {


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

    private Integer zhoushu;  //需要穿的轴数

    @ManyToOne
    @JoinColumn(name = "youxianji_id")
    private Dict youxianji; //优先级


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status;//状态








    private String beizhu;   //备注



    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    @JsonIgnoreProperties("jiHuaChuanZongMain")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "jihua_chuanzong_main_id")
    private List<JiHua_ChuanZong> jiHua_chuanZongs; //合约号

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//下单开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//下单结束日期

}
