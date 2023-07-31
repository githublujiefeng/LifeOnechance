package com.learn.schedul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class SchedulTaskFactory implements SchedulingConfigurer {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String,Class> schedulClasses;

    private int i;

    @Scheduled(cron = "*/15 * * * * ?")
    @Async
    public void execute() {
        logger.info("thread id:{},FixedPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(()->{
            System.out.println("子线程");
        },triggerContext->{
            return new CronTrigger("").nextExecutionTime(triggerContext);
        });
    }

}
