package com.ijinge.Idempotent.Annotation;

import com.ijinge.Idempotent.Consistent.IdempotentSceneEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    /**
     * 使用场景 MQ 、RESTAPI
     */
    IdempotentSceneEnum scene() default IdempotentSceneEnum.RESTAPI;

    /**
    *  使用的Token key
    */
    String tokenKey() default "Token";

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "您操作太快，请稍后再试";
}
