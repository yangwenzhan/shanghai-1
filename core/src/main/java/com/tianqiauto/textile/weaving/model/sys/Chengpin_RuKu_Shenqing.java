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
@Entity(name = "sys_chengpin_ruku_shenqing")
@EqualsAndHashCode(exclude = {"heyuehao","laiyuan","cangkuquerenren","status"})
@ToString(exclude = {"heyuehao","laiyuan","cangkuquerenren","status"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chengpin_RuKu_Shenqing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;



    @ManyToOne
    @JoinColumn(name="laiyuan_id")
    private Dict laiyuan; //来源


    private Double changdu; //长度


    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 入库申请状态


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
