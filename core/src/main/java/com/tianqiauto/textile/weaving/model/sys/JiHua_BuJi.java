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
 * @Description 布机计划单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jihua_buji")
@EqualsAndHashCode(exclude = {"banci","leixing","jitaihao","zhizhou","heyuehao","status","danshuangzhou","zhiXingBuJi"})
@ToString(exclude = {"banci","leixing","jitaihao","zhizhou","heyuehao","status","danshuangzhou","zhiXingBuJi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JiHua_BuJi {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Date riqi; //计划日期

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "leixing_id")
    private Dict leixing;  //接经/改支/剪轴

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao;  //机台号

    private String zhizhou;


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    @ManyToOne
    @JoinColumn(name = "danshuangzhou_id")
    private Dict danshuangzhou;  //单轴/双轴

    private Integer youxianji; //优先级

    private Integer deleted; //删除标识
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status;//状态


    @OneToOne(mappedBy="jiHua_buJi")
    @JsonIgnoreProperties("jiHua_buJi")
    private ZhiXing_BuJi zhiXingBuJi;







    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    private String beizhu;   //备注

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//结束日期
}
