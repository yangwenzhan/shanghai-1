package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Gongxu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName Order
 * @Description 织轴
 * @Author xingxiaoshuai
 * @Date 2019-02-14 09:21
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_beam_zhizhou")
public class Beam_ZhiZhou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String zhouhao; //轴号

    @ManyToOne
    @JoinColumn(name = "jixing_id")
    private Gongxu jixing; //机型


    @Column(scale = 2)
    private Double zhoukuan; //轴宽



    private String beizhu;   //备注




}
