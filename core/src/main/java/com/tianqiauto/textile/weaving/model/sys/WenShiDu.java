package com.tianqiauto.textile.weaving.model.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_wenshidu")
public class WenShiDu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  //名称

    private String weizhi;  //位置

    @Column(precision = 2)
    private Double wendu;  //温度
    @Column(precision = 2)
    private Double shidu; //湿度


    @Column
    @LastModifiedDate
    private Date lastModifiedDate;




}
