package com.learn.thread.parentThread;

public class ParentThreadLocal {
    static class getParentThread{
        public static void main(String[] args) {
            Thread parentThread = new Thread(()->{
                //threadLocal是什么？干什么用的
                //
               ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
               threadLocal.set(1);

               InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();
               inheritableThreadLocal.set(2);
            });
        }
    }
}
