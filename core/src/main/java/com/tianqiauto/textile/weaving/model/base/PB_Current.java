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
@Entity(name = "base_pb_current")
@EqualsAndHashCode(exclude = {"gongxu","lunban","banci"})
@ToString(exclude = {"gongxu","lunban","banci"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PB_Current {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "gongxu_id")
    private Gongxu gongxu;

    private Date  riqi;
    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;
    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;

    private Date bancikaishishijian;
    private Date bancijieshushijian;


}
