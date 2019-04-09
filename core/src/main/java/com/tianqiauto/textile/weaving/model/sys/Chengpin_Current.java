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
 * @Description 成品仓库实时状况
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chengpin_current")
@EqualsAndHashCode(exclude = {"heyuehao"})
@ToString(exclude = {"heyuehao"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chengpin_Current {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private Double changdu; //长度



}
