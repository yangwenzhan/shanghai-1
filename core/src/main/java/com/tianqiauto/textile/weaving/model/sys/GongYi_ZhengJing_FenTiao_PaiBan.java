package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName GongYi_ZhengJing_FenTiao_PaiBan
 * @Description 排版是根据排花进行排列，每一条的根数都是排花的整数倍
 * @Author xingxiaoshuai
 * @Date 2019-04-13 15:31
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_gongyi_zhengjing_fentiao_paiban")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GongYi_ZhengJing_FenTiao_PaiBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String tiaoshu; //第几条或n-m条

    private Integer genshu;  //根数


    private String beizhu; //备注



}
