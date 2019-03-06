package com.tianqiauto.textile.weaving.model.base;

import com.tianqiauto.textile.weaving.model.base.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_dict_type")
/**
 * 数据字典类型表
 */
public class Dict_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private Integer fixed;  //自己类型不能修改fixed为1，能修改fixed为0。（默认为0）



    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Set<Dict> dicts;









}
