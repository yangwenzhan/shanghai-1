package com.tianqiauto.textile.weaving.util.procedure.core;

/**
 * @Description: 响应结果生成工具
 * @Author: 李晓晓
 * @CreateDate: 2018/11/22 17:06
 */
@SuppressWarnings("rawtypes")
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

	public static ProcedureResult genSuccessResult() {
        return new ProcedureResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    @SuppressWarnings("unchecked")
	public static <T> ProcedureResult<T> genSuccessResult(T data) {
        return new ProcedureResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static ProcedureResult genFailResult(String message) {
        return new ProcedureResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}
