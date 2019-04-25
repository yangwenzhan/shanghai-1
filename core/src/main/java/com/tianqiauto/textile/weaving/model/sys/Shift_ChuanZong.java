package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Shift_Zhengjing
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-28 14:57
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_shift_chuanzong")
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao","users"})
@ToString(exclude = {"banci","jitaihao","heyuehao","users"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shift_ChuanZong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date riqi;
    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号  为空代表为手工穿综

    @ManyToOne
    @JoinColumn(name = "zhiZhou_id")
    private Beam_ZhiZhou zhiZhou; //轴号

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;


    private Integer genshu; //根数

    private Integer shifouwancheng;  //是否完成：为1时完成 为0时未完成


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_shift_chuanzong_user", joinColumns = @JoinColumn(name = "shift_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;






}
