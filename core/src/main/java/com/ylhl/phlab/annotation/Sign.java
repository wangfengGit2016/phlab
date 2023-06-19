package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * @Author: zhengyq
 * @Date:2021/12/22 15:32
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Sign {
}
