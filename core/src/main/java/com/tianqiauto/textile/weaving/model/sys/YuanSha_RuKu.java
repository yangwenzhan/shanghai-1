package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha
 * @Description 原纱入库信息
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_yuansha_ruku")
public class YuanSha_RuKu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "yuansha_id")
    private YuanSha yuanSha;

    @OneToOne
    @JoinColumn(name = "yuansha_ruku_shenqing_id")
    private YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing;


    private Date goururizhi; //购入日期

    @ManyToOne
    @JoinColumn(name="laiyuan_id")
    private Dict laiyuan; //来源     外购/来料加工/车间退库

    private Integer baoshu; //包数

    private Double baozhong; //包重

    private Double zongzhong; //总重量








    private String beizhu; //备注
    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;


}
