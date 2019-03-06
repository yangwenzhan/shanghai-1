package com.tianqiauto.textile.weaving.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-18 16:39
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private int code;
    private String message;
    private Object data;

    public static Result ok(String message, Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static Result error(String message, Object data) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static Result result(int code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


}
