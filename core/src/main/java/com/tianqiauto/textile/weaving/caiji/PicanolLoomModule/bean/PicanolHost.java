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
 * @Date 2019/3/7 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "picanol_host")
public class PicanolHost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @LastModifiedDate
    private Date lastModifyTime;

    private String ip;

    private String machineNumber;

    private int port;

}
