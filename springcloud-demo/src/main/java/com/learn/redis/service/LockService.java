package com.learn.redis.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class LockService {
    @Resource
    private RedissonClient redissonClient;

    public<T> T executeWithLockThrows(String key, int waitTime, TimeUnit unit, Supplier<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new Throwable("获取锁失败");
        }
        try{
            //执行锁内的代码逻辑
            return supplier.get();
        }finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static String getk(){
        return "1";
    }
}
