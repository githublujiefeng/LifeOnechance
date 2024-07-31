package com.learn.thread.parentThread;

import java.util.concurrent.atomic.AtomicLong;

public class ParentThreadLocal {
    static class getParentThread{
        public static void main(String[] args) {
            Thread parentThread = new Thread(()->{
                //threadLocal是什么？
                //这个类提供线程局部变量
               ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
               threadLocal.set(1);
                //该类扩展了ThreadLocal以提供从父线程到子线程的值的继承：
                // 当子线程被创建时，子接收到父值具有值的所有可继承的线程局部变量的初始值。
               InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();
               inheritableThreadLocal.set(2);
               new Thread(()->{
                   System.out.println("threadLocal="+threadLocal.get());
                   System.out.println("inheritableThreadLocal="+inheritableThreadLocal.get());
               }).start();
            },"父线程");
            parentThread.start();
            new AtomicLong(0).getAndIncrement();
        }
    }
}
