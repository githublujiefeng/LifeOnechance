package com.learn.Proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CheckWhoInvoctionHandler implements InvocationHandler {
    private Object object;

    public CheckWhoInvoctionHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object res = method.invoke(object,args);
        System.out.println("正在代理校验你是谁！");
        return res;
    }


}
