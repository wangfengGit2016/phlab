package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * @author zhengyq
 * 简介说明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE })
public @interface Comment {
    String value()  default "";
}
