package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Gongxu;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

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
@EqualsAndHashCode(exclude = {"jixing"})
@ToString(exclude = {"jixing"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Beam_ZhiZhou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String zhouhao; //轴号

    @ManyToOne
    @JoinColumn(name = "jixing_id")
    private Gongxu jixing; //机型


    private Integer zhoukuan; //轴宽



    private String beizhu;   //备注




}
