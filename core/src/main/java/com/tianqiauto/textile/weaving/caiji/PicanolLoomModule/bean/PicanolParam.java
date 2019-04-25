package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.sys.Current;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author bjw
 * @Date 2019/3/6 16:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Picanol_Param")
@EqualsAndHashCode(exclude = {"current"})
@ToString(exclude = {"current"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PicanolParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @LastModifiedDate
    private Date lastModifyTime;

    private String machineNumber; //机器编号
    private String name;           //参数名
    private String value;          //参数值

    private String paramNumber;   //参数编号

    @OneToOne
    @JoinColumn(name = "current_id")
    private Current current;
}
