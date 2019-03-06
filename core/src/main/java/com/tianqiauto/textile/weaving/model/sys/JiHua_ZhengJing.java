package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Entity(name = "sys_jihua_zhengjing")
public class JiHua_ZhengJing {


    /**
     *
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "jiHua_zhengJing_main_id")
    private JiHua_ZhengJing_Main jiHua_zhengJing_main;


    //浆纱计划下达完后下达整经计划。（根据轴数生成记录数）。输入：日期、班次、机台、优先级、备注


    //整经工下机确认时：判断该条整经计划的浆纱计划是否为空：为空时生成序号、不为空时不需要生成序号
    @ManyToOne
    @JoinColumn(name = "jihua_jiangsha_id")
    private JiHua_JiangSha jiHua_jiangSha;



    //直接下达整经计划，输入：日期、班次、机台、合约号、轴数（有几个轴后台生成几条记录）、优先级、备注

   // 整经下机确认：序号（日期+时间+班次+机台号+合约号+轴号）
    private Date riqi; //计划整经完成日期

    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;


    private Integer Youxianji; //优先级 1最高 2 3 4




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
