package com.tianqiauto.textile.weaving.util.JPASql;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/20 16:12
 */
public class Container {

    private List<Object> paramList;

    private String sql;

    Container() {
        paramList = new ArrayList<>();
    }

    protected void setParamList(Object obj) {
        paramList.add(obj);
    }

    protected void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParam() {
        return paramList.toArray();
    }

    public String getSql() {
        return sql;
    }
}
