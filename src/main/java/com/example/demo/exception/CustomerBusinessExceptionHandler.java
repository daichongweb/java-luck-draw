package com.example.demo.exception;

import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.validation.BindException;

/**
 * 全局处理异常返回类
 */
@ControllerAdvice
public class CustomerBusinessExceptionHandler {

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public JsonResult businessExceptionHandler(BusinessException e) {
        return new JsonResult<>(String.valueOf(e.getCode()), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public JsonResult BadRequest(BindException e) {
        return new JsonResult<>("200", e.getFieldError().getDefaultMessage());
    }

}
