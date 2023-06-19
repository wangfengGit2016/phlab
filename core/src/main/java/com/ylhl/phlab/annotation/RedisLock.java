package com.ylhl.phlab.annotation;

import java.lang.annotation.*;

/**
 * @author zhengyq
 * Redis锁
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE })
public @interface RedisLock {
    /**
     * 指定key
     */
    String key() default "";
    /**
     * 使用参数 多个请使用‘,’分割, 如需使用请求对象值则param.XX
     *
     * 例1：param.mcId,param.orderId
     * 例2：mcId
     * 例3：12345
     */
    String params() default "";

    /**
     * 锁住时长(毫秒)默认0,即方法执行完成即解锁，大于0则不解锁
     */
    long timeLock() default 5L;
}
