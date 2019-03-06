package com.tianqiauto.textile.weaving.model.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "base_log")
@EntityListeners(AuditingEntityListener.class)
/**
 * 系统日志表
 */
public class Sys_Log {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;  // 请求url
    private String http_method; //get/post等
    private String ip;  //请求ip地址
    private String class_method; //类+方法
    private String args; //请求参数列表

    private String type; //类型（区分异常信息，异常的值为exception）

    private Long executeTime; //执行时间（毫秒）

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String response; //返回值


    private String msg;  //描述信息

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String exception;   //异常信息


    private String userInfo;  //操作用户信息



    @CreatedDate
    private Date createTime;




}
