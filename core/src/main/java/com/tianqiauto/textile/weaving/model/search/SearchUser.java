package com.tianqiauto.textile.weaving.model.search;

import lombok.Data;

/**
 * @ClassName SearchUser
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-01 10:43
 * @Version 1.0
 **/

@Data
public class SearchUser {

    private Long gongxu; //工序id

    private Long role;  //角色id

    private Long lunban; //轮班id

    private Integer zu;   //组

    private Integer shifouzaizhi;  //是否在职

    private String username;  //工号



}
