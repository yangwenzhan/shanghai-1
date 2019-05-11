package com.tianqiauto.textile.weaving.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PanCunYue
 * @Description 盘存月表
 * @Author xingxiaoshuai
 * @Date 2019-02-27 17:37
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_pancunyue")
public class PanCunYue {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nian; //年

    private String yue;  //月

    private String kaishiriqi;

    @ManyToOne
    @JoinColumn(name = "kaishibanci_id")
    private Dict kaishibanci;

    private String jieshuriqi;

    @ManyToOne
    @JoinColumn(name = "jieshubanci_id")
    private Dict jieshubanci;


    private String kaishi; //开始时间+班次

    private String jieshu; //结束时间+班次


    public void setKaishi(String kaishiriqi,Dict kaishibanci){
        this.kaishi = kaishiriqi.replaceAll("-","")+kaishibanci.getValue();
    }

    public void setJieshu(String jieshuriqi,Dict jieshubanci){
        this.jieshu = jieshuriqi.replaceAll("-","")+jieshubanci.getValue();
    }



}
