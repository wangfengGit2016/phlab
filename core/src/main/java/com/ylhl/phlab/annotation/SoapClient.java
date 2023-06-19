package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * @author zhengyq
 * @date 2022/4/13 16:38
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SoapClient {
    String name() default "";
    String value() default "";
    String url() default "";
}
