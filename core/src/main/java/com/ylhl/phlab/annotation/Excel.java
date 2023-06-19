package com.ylhl.phlab.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 表名
 * @author zhengyq
 * @date 2021/12/4 5:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Excel {
    String value() default "";
    String type() default "";
    int title() default 0;
    int data() default 1;
}
