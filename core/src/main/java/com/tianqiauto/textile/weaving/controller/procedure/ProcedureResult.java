package com.tianqiauto.textile.weaving.controller.procedure;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 统一API响应结果封装
 * @Author: 李晓晓
 * @CreateDate: 2018/11/22 17:06
 */
@SuppressWarnings("rawtypes")
public class ProcedureResult<T> {
    private int code;
    private String message;
    private T data;

	public ProcedureResult setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ProcedureResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ProcedureResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
