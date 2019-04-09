package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @ClassName TV_DianShiFangAn
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/8 17:57
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_tv_dianshifangan")
@EqualsAndHashCode(exclude = {"zhanShiYeMians"})
public class TV_DianShiFangAn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //方案名称

    private String weizhi; //电视位置

    @JsonIgnoreProperties("fangAns")
    @ManyToMany(mappedBy = "fangAns")
    private Set<TV_ZhanShiYeMian> zhanShiYeMians;

}
