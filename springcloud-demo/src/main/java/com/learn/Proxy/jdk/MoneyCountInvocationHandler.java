package com.learn.Proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MoneyCountInvocationHandler implements InvocationHandler {
    /**
     * 被代理目标
     */
    private final Object target;
    /**
     * 花费总数
     */
    private Double moneyCount;

    public MoneyCountInvocationHandler(Object target) {
        this.target = target;
        this.moneyCount = 0.0;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        Object result = method.invoke(target,args);
        moneyCount +=0.07;
        System.out.println("学习短信代理花费了"+moneyCount);
        return result;
    }
}
