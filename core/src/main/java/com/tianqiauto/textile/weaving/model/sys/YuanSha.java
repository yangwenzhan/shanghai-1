package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha")
@EqualsAndHashCode(exclude = {"yuanSha_zhiLiang","gongyingshang","baozhuangxingshi"})
@ToString(exclude = {"yuanSha_zhiLiang","gongyingshang","baozhuangxingshi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YuanSha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 原纱入库查询
     *
     * 原纱库存查询
     *
     *
     * 修改原纱入库信息时，总重量必须大于等于相同批次的出库总重量
     *
     * 批号生成规则：201903301  yyyymmdd+自增数字
     */


    private String pinming; //品名

    private String pihao; //批号！！！！

    @ManyToOne
    @JoinColumn(name = "gongyingshang_id")
    private Dict gongyingshang; //供应商

    private Integer zhishu;  //支数

    private String sehao; //色号

    private String sebie; //色别

    @ManyToOne
    @JoinColumn(name = "baozhuangxingshi_id")
    private Dict baozhuangxingshi; //包装形式

    private Double kucunliang; //库存量 （出入库时需要进行更新）

    private Double baozhong; //包重



    @OneToOne
    @JoinColumn(name = "yuansha_zhiliang_id")
    @JsonIgnoreProperties("yuansha")
    private YuanSha_ZhiLiang yuanSha_zhiLiang;


    private String beizhu; //备注


    @Column
    @CreatedDate
    private Date createTime;
    @Column
    @CreatedBy
    private String  luruRen;
    @Column
    @LastModifiedDate
    private Date lastModifyTime;
    @Column
    @LastModifiedBy
    private String lastModifyRen;


}
