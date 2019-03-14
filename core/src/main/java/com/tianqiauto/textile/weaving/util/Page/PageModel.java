package com.tianqiauto.textile.weaving.util.Page;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @Author bjw
 * @Date 2019/3/12 15:01
 */
@Data
public class PageModel<T> {

    private T model;

    private Boolean sffy = true; //是否需要分页，true是，false否；默认分页

    private Integer ksts;

    private Integer jsts;

    private String ksrq;

    private String jsrq;

}
