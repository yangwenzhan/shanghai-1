package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
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
@Entity(name = "sys_jihua_jiangsha_main")
public class JiHua_JiangSha_Main {


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

    private String ganghao; //浆纱缸号


    private Integer zongjingchang; //总经长
    private Integer zongjinggenshu; //总经根数
    private Integer zongjingzhoushu; //经轴数(不需要浆纱默认1)
    private Integer danzhoutoufen; //单轴头份（自动计算出来填充，不需要浆纱的即为总经根数）

    private Integer zongzhizhoushu; //总织轴数

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




}
