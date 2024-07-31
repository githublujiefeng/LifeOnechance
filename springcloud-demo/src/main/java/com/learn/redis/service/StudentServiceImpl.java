package com.learn.redis.service;

import com.learn.redis.Annotation.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class StudentServiceImpl {

    @Resource
    RedissonClient redissonClient;

    public void lockTest2(Integer goodsId) throws  InterruptedException{
        RLock lock = redissonClient.getLock(String.valueOf(goodsId));
        boolean lockSuccess = lock.tryLock(500, TimeUnit.MILLISECONDS);
        if(!lockSuccess){
            throw new InterruptedException("获取锁失败");
        }
        try {
            System.out.println("RPC调用-1，XXX");
        }finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    @RedissonLock(key="#goodsId")
    public void lockTest1(Integer goodsId){
        System.out.println("调用aop锁！");
    }
}
