package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha_ChuKu
 * @Description 原纱入库申请单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_ruku_shenqing")
public class YuanSha_RuKu_Shenqing {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
   private YuanSha yuanSha; //原纱


    @ManyToOne
    @JoinColumn(name="laiyuan_id")
    private Dict laiyuan; //来源     外购/来料加工/车间退库

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量



    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 入库申请状态


    @ManyToOne
    @JoinColumn(name = "cangkuquerenren_id")
    private User cangkuquerenren;  //仓库确认人
    private Date cangkuquerenshijian; //仓库确认时间



    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;  //哪个合约号上退的







    private String beizhu; //备注


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;


}
