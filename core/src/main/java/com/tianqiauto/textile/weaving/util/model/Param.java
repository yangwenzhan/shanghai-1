package com.tianqiauto.textile.weaving.util.model;

import javax.persistence.criteria.Path;

/**
 * @Author bjw
 * @Date 2019/4/16 20:54
 */
public class Param {
    private boolean isEmpty;

    private Object value;

    private Path<Object> path;

    public Param(boolean isEmpty, Object value, Path<Object> path) {
        this.isEmpty = isEmpty;
        this.value = value;
        this.path = path;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Object getValue() {
        return value;
    }

    public Path<Object> getPath() {
        return path;
    }
}
