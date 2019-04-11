package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao"})
@ToString(exclude = {"banci","jitaihao","heyuehao"})
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
    private SheBei jitaihao; //机台号

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;


    private Integer genshu; //根数





}
