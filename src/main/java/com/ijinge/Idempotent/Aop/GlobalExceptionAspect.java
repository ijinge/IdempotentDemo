package com.ijinge.Idempotent.Aop;

import com.ijinge.Idempotent.Entity.R;
import com.ijinge.Idempotent.Exception.BizException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAspect {
    // 捕获业务异常
    @ExceptionHandler(BizException.class)
    public R<Void> handleBizException(BizException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    // 捕获参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors()
                .stream().findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("参数错误");
        return R.fail(400, msg);
    }

    // 捕获其他异常
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        System.out.println(e.getMessage());
        return R.fail(500, "服务器异常");
    }
}