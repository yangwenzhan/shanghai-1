package com.tianqiauto.textile.weaving.model.sys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName CheWei_History
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-12 17:59
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_chewei")
@EqualsAndHashCode(exclude = {"users","jitaihao","lunban","banci"})
@ToString(exclude = {"users","jitaihao","lunban","banci"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CheWei_History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date riqi;
    @ManyToOne
    @JoinColumn(name = "banci_id")
    private Dict banci;

    @ManyToOne
    @JoinColumn(name = "jitai_id")
    private SheBei jitaihao; //机台号

    @ManyToOne
    @JoinColumn(name = "lunban_id")
    private Dict lunban;  //轮班

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_chewei_yuangong", joinColumns = @JoinColumn(name = "chewei_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;





}
