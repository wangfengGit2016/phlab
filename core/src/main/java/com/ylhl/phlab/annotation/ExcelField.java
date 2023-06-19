package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * 字段别名及默认值
 * @author zhengyq
 * @date 2021/12/4 5:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {
    String alias() default "";
    String value() default "";
}
