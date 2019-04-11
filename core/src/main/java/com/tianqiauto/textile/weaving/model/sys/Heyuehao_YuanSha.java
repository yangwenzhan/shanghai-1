package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
@EqualsAndHashCode(exclude = {"weisha","jingsha","yuanSha"})
@Entity(name = "sys_heyuehao_yuansha")
//合约号可以直接关联原纱表，但是考虑到合约号关联原纱还有根数等其他信息，中间又多了这个表
@ToString(exclude = {"weisha","jingsha","yuanSha"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @JsonIgnoreProperties("jingsha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_jingsha",joinColumns = @JoinColumn(name = "jingsha_id"),
            inverseJoinColumns = @JoinColumn(name = "heyuehao_id"))
    private Set<Heyuehao> jingsha; //经纱

    @JsonIgnoreProperties("weisha")
    @ManyToMany
    @JoinTable(name = "sys_heyuehao_yuansha_weisha",joinColumns = @JoinColumn(name = "weisha_id"),
            inverseJoinColumns = @JoinColumn(name = "heyuehao_id"))
    private Set<Heyuehao> weisha; //纬纱

}
