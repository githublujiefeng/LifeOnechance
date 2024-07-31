package com.learn.schedul;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TaskTest implements ITaskService, Condition {

    @Async //异步实现定时请求
    @Override
    public void doService() {
        log.info("定时任务手动测试！时间：{}", LocalDateTime.now().toLocalTime());
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ThreadLocal local = new ThreadLocal();
        local.remove();
        return false;
    }
}
