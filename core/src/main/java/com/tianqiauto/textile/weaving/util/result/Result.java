package com.tianqiauto.textile.weaving.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
    private Long count;

    public static Result ok(String message, Object data) {
        Result result = new Result();
        result.setCode(0);
        result.setMessage(message);

        if(data instanceof Page){
            result.setCount(((Page) data).getTotalElements());
            result.setData(((Page) data).getContent());
        }else{
            result.setData(data);
        }

        return result;
    }
    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(0);
        if(data instanceof Page){
            result.setCount(((Page) data).getTotalElements());
            result.setData(((Page) data).getContent());
        }else{
            result.setData(data);
        }
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

        if(data instanceof Page){
            result.setCount(((Page) data).getTotalElements());
            result.setData(((Page) data).getContent());
        }else{
            result.setData(data);
        }

        return result;
    }


}
