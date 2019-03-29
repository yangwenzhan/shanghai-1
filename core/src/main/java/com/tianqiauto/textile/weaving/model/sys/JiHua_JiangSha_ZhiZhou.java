package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Order
 * @Description 浆纱计划对应织轴信息
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jihua_jiangsha_zhizhou")
@EqualsAndHashCode(exclude = {"JiHuaJiangShas"})
public class JiHua_JiangSha_ZhiZhou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jihua_jiangsha_id")
    @JsonIgnoreProperties("jihuajiangshazhizhous")
    private JiHua_JiangSha jiHua_jiangSha;


    //不同机型对应缩小选择织轴范围
    @ManyToOne
    @JoinColumn(name = "jixing_id")
    private Gongxu jixing;  //布机机型

    private String danshuangzhou;  //单轴/双轴

    private Integer zhoushu; //轴数

    private Integer changdu; //长度


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    private String beizhu;   //备注


}
