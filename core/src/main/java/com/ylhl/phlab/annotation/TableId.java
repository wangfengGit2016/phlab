package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * @author zhengyq
 * 标记主键
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE })
public @interface TableId {
    String type()  default "UUID";
    String alias() default "";
}
