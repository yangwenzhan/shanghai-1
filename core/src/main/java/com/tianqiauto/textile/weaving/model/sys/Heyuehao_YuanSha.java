package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Heyuehao
 * @Description 合约号对应经纱
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:26
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"heyuehao_jingsha","heyuehao_weisha"})
@Entity(name = "sys_heyuehao_yuansha")
//合约号可以直接关联原纱表，但是考虑到合约号关联原纱还有根数等其他信息，中间又多了这个表
public class Heyuehao_YuanSha {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "yuansha_id")
    private YuanSha yuanSha; //原纱

    private Integer genshu;  //根数

    private String leixing; //经纱、纬纱



    private String beizhu; //备注


    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    @JsonIgnoreProperties("yuansha_jingsha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_jingsha",joinColumns = @JoinColumn(name = "jingsha_id"),
            inverseJoinColumns = @JoinColumn(name = "heyuehao_id"))
    private Set<Heyuehao> jingsha; //经纱

    @JsonIgnoreProperties("yuansha_weisha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_weisha",joinColumns = @JoinColumn(name = "weisha_id"),
            inverseJoinColumns = @JoinColumn(name = "heyuehao_id"))
    private Set<Heyuehao> weisha; //纬纱

}
