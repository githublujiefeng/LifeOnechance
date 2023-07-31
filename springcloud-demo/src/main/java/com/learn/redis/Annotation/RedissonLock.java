package com.learn.redis.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedissonLock {
    /**
     * key前缀，默认取方法全限定名
     * @return
     */
    String prefixKey() default "";

    /**
     * EL 表达式
     * @return
     */
    String key();

    /**
     * 等待锁的时间，默认-1；不等待直接失败，redisson默认也是-1
     * @return
     */
    int waitTime() default -1;

    /**
     * 等待锁的时间单位，默认毫秒
     * @return
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
