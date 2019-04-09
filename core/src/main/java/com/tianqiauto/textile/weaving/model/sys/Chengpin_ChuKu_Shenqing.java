package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha_ChuKu
 * @Description 成品出库申请单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chengpin_chuku_shenqing")
@EqualsAndHashCode(exclude = {"heyuehao","chukuleixing","yingxiaoyuan","status","cangkuquerenren"})
@ToString(exclude = {"heyuehao","chukuleixing","yingxiaoyuan","status","cangkuquerenren"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chengpin_ChuKu_Shenqing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;


    private Date yaoqiulingyongshijian; //要求领用时间
    private String gouhuodanwei;    //购货单位
    private String shouhuodanwei;  //收货单位
    private String lianxiren;   //联系人
    private String lianxidianhua; //联系电话
    private String shouhuodizhi; //收货地址


    @ManyToOne
    @JoinColumn(name = "chukuleixing_id")
    private Dict chukuleixing; //出库类型


    private Double changdu; //长度


    @ManyToOne
    @JoinColumn(name = "yingxiaoyuan_id")
    private User yingxiaoyuan; //营销员


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 出库申请状态


    @ManyToOne
    @JoinColumn(name = "cangkuquerenren_id")
    private User cangkuquerenren;  //仓库确认人
    private Date cangkuquerenshijian; //仓库确认时间




    private String beizhu; //备注


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;


}
