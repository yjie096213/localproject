package com.htinf.sm.common.util;

import com.htinf.sm.common.model.ArgumentInvalidResult;
import com.htinf.sm.common.model.ResultValue;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GlobalExceptionHandler
 * @ProjectName: server_manage
 * @Description: 验证属性全局异常处理类
 * @Author: Administrator
 * @DATE: 2021/7/5 19:47
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    //添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {

        //按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }

        ResultValue resultValue = new ResultValue(9000, invalidArguments, "参数验证有误");
        return resultValue;
    }
}
