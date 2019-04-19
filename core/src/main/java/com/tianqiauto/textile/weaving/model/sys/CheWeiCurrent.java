package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author xingxiaoshuai
 * @Date 2019-04-12 16:19
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chewei_current")
@EqualsAndHashCode(exclude = {"users","jitaihao","lunban"})
@ToString(exclude = {"users","jitaihao","lunban"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheWeiCurrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao; //机台号

    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;  //轮班

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_chewei_current_yuangong", joinColumns = @JoinColumn(name = "chewei_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;



}
