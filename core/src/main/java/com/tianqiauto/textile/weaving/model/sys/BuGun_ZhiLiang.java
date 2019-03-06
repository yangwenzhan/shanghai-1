package com.tianqiauto.textile.weaving.model.sys;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName BuGun
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-26 17:15
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "sys_bugun_zhiliang")
public class BuGun_ZhiLiang {


    //布辊瑕疵类型、个数

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





}
