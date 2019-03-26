package com.tianqiauto.textile.weaving.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "base_user_yuangong")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user","gongxu","lunban"})
public class User_YuanGong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //一对一
    @JsonIgnoreProperties("user_yuanGong")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    //关联工序表 可为空
    @ManyToOne
    @JoinColumn(name = "gongxu_id")
    private Gongxu gongxu;

    //若工序为空，则组必为空，新增时不需展示组
    private Integer zu;

    //所属轮班
    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;


}
