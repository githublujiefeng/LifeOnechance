package com.learn.schedul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;


@Component
@Slf4j
public class SchedulTaskService {

    @Autowired
    ApplicationContext applicationContext;
    /**
     * 以下两个都是线程安全的集合类。
     */
    public static Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();
    public List<SchedulDTO> taskList = new CopyOnWriteArrayList<SchedulDTO>();

    private final ThreadPoolTaskScheduler syncScheduler;

    public ThreadPoolTaskScheduler getSyncScheduler(){
        ThreadPoolTaskScheduler syncScheduler = new ThreadPoolTaskScheduler();
        syncScheduler.setPoolSize(10);
        //设置线程名字，方便查看排查问题
        syncScheduler.setThreadGroupName("syncThreadPool");
        syncScheduler.setThreadNamePrefix("syncThread-");
        syncScheduler.initialize();
        return syncScheduler;
    }


    public SchedulTaskService() {
        this.syncScheduler = getSyncScheduler();
        init();
    }

    private void init(){
//        SchedulDTO schedulDTO = new SchedulDTO();
//        schedulDTO.setName(TaskTest.class.getName());
//        schedulDTO.setCorn("*/5 * * * * ?");
//        schedulDTO.setSchedulClass(TaskTest.class);
//        add(schedulDTO);

    }
    //注册任务
    public boolean add(SchedulDTO schedulDTO){
        if(null != taskMap.get(schedulDTO.getName())){
            if(!stopTask(schedulDTO.getName())){
                log.info("存在线程但是没有删除成功！");
            }
        }
        ScheduledFuture<?> future = syncScheduler.schedule(
                //不需要用代理对象实现。
//                (Runnable) Proxy.newProxyInstance(
//                        this.getClass().getClassLoader(), schedulDTO.getSchedulClass().getInterfaces(), new InvocationHandler() {
//                            @Override
//                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                                Object instance = schedulDTO.getSchedulClass().newInstance();
//                                return method.invoke(instance, args);
//                            }
//                        })
                new Runnable() {
                    @Override
                    public void run() {
                        //通过名字，使用ApplicationContext获取bean对象
                        ITaskService bean = (ITaskService)applicationContext.getBean(schedulDTO.getName());
                        bean.doService();
                    }
                }
                , triggerContext -> {
                    return new CronTrigger(schedulDTO.getCorn()).nextExecutionTime(triggerContext);
                });
        taskMap.put(schedulDTO.getName(),future);
        taskList.add(schedulDTO);
        log.info("添加成功！");
        return true;
    }

    //关闭任务
    public boolean stopTask(String name){
        if(taskMap.get(name) ==null){
            return false;
        }
        ScheduledFuture<?> scheduledFuture = taskMap.get(name);
        scheduledFuture.cancel(true);
        taskMap.remove(name);
        Optional<SchedulDTO> first = taskList.stream().filter(schedulDTO -> name.equals(schedulDTO.getName())).findFirst();
        first.get().setStatus(1);
        return true;
    }

    public static void main(String[] args) {
        log.info("name111:{}",TaskTest.class.getName());
    }
}
