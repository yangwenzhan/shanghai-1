package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName Shift_Zhengjing
 * @Description 布机产量
 * @Author xingxiaoshuai
 * @Date 2019-02-28 14:57
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_shift_buji")
@EqualsAndHashCode(exclude = {"banci","jitaihao","heyuehao","users"})
@ToString(exclude = {"banci","jitaihao","heyuehao","users"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shift_BuJi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date riqi;
    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;
    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;


    private String xuhao; //日期班次拼出来的


    @ManyToOne
    @JoinColumn(name = "shebei_id")
    private SheBei jitaihao; //机台号

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private Date bancikaishishijian; //班次开始时间
    private Date bancijieshushijian; //班次结束时间


    private  Integer lilunshichang; //理论时长

//    public void setLilunshichang(Integer lilunshichang) {
//        this.lilunshichang = lilunshichang;
//    }

    private Date kaishishijian; //布辊开始时间
    private Date jieshushijian; //布辊结束时间


    private Double changdu; //长度

    private Double xiaolv; //效率


    private Integer daweicishu; //打纬次数

    private Integer jingtingcishu; //经停次数
    private Integer weitingcishu; //纬停次数
    private Integer qitatingcishu; //其他停次数
    private Integer zongtingcishu; //总停次数
    private Integer jingtingshijian; //经停时间
    private Integer weitingshijian; //纬停时间
    private Integer qitatingshijian; //其他停时间
    private Integer zongtingshijian; //总停时间



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_shift_buji_user", joinColumns = @JoinColumn(name = "shift_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;



    @Column
    @CreatedDate
    private Date createTime;
    @Column
    @LastModifiedDate
    private Date lastModifyTime;
    @Column
    @LastModifiedBy
    private String lastModifyRen;


}
