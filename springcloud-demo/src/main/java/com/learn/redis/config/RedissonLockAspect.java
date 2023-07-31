package com.learn.redis.config;

import com.learn.redis.Annotation.RedissonLock;
import com.learn.redis.service.LockService;
import com.learn.redis.util.SpElUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class RedissonLockAspect {

    @Resource
    LockService lockService;

    //private LockService lockService;
    @Pointcut("@annotation(com.learn.redis.Annotation.RedissonLock)")
    private void pointcut(){
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        //默认方法限定名+注解排名（可能多个）
        String prefix = StringUtils.isEmpty(redissonLock.prefixKey())?
                SpElUtils.getMethodKey(method) :redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method,joinPoint.getArgs(),redissonLock.key());
         return lockService.executeWithLockThrows(prefix+":"+key,redissonLock.waitTime(),
                redissonLock.unit(),joinPoint::proceed);
    }
}
