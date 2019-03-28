package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author bjw
 * @Date 2019/3/6 16:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Picanol_Param")
public class PicanolParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @LastModifiedDate
    private Date lastModifyTime;

    private String machineNumber; //机器编号
    private String name;           //参数名
    private String value;          //参数值

}
