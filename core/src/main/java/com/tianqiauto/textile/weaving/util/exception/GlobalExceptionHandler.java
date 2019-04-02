package com.tianqiauto.textile.weaving.util.exception;

import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-18 16:36
 * @Version 1.0
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){

        String msg = "服务器异常，请重试或联系管理员";

        if("Access is denied".equals(e.getMessage())){
            msg = "没有权限访问此页面，请联系管理员";
        }
        e.printStackTrace();

        e.printStackTrace();

        return Result.error(msg,e.getMessage());
    }


    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String,Object> map = new HashMap();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        errors.stream().forEach(error -> {
            FieldError fieldError = (FieldError)error;
//                String message = fieldError.getField()+" : "+fieldError.getDefaultMessage();
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        //服务器端验证客户端传递数据有问题返回code为444。
        return Result.result(444,"请检查输入数据的正确性",map);
    }







}
