package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * 表名
 * @author zhengyq
 * @date 2021/12/4 5:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
    String value();
}
