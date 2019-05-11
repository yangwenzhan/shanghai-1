package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.sys.Current;
import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author bjw
 * @Date 2019/3/7 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "picanol_host")
@EqualsAndHashCode(exclude = {"currentBuJi"})
@ToString(exclude = {"currentBuJi"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PicanolHost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @LastModifiedDate
    private Date lastModifyTime;

    private String ip;

    private String machineNumber;

    private int port;

    @OneToOne
    @JoinColumn(name = "currentBuJi_id")
    private Current_BuJi currentBuJi;

}
