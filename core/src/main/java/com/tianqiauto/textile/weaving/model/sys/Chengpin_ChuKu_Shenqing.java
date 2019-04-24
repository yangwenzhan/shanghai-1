package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName YuanSha_ChuKu
 * @Description 成品出库申请单
 * @Author xingxiaoshuai
 * @Date 2019-02-14 10:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chengpin_chuku_shenqing")
@EqualsAndHashCode(exclude = {"status","chengpinchuku"})
@ToString(exclude = {"status","chengpinchuku"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chengpin_ChuKu_Shenqing {


    @OneToOne(mappedBy="chengpin_chuKu_shenqing",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("chengpin_chuKu_shenqing")
    private Chengpin_ChuKu chengpinchuku;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double changdu; //长度

    private String beizhu; //备注

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Dict status; // 出库申请状态



    @CreatedDate
    private Date createTime;
    private String  luruRen;
    @LastModifiedDate
    private Date lastModifyTime;
    private String lastModifyRen;

    //查询使用条件
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date kaishiriqi;//开始日期

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date jieshuriqi;//结束日期

}
