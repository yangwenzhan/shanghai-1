package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author xingxiaoshuai
 * @Date 2019-02-14 16:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_pb_history")
@EqualsAndHashCode(exclude = {"gongxu"})
@ToString(exclude = {"gongxu"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PB_Current {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "gongxu_id")
    private Gongxu gongxu;

    private Date  riqi;
    private Dict banci;
    private Dict lunban;

    private Date bancikaishishijian;
    private Date bancijieshushijian;


}
