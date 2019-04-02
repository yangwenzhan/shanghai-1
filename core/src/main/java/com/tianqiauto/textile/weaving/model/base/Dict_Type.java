package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_dict_type")
@EqualsAndHashCode(exclude = {"dicts"})
/**
 * 数据字典类型表
 */
public class Dict_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String code;


    private Integer fixed;  //自己类型不能修改fixed为1，能修改fixed为0。（默认为0）



    @OneToMany(mappedBy = "dict_type",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("dict_type")
    private Set<Dict> dicts;









}
