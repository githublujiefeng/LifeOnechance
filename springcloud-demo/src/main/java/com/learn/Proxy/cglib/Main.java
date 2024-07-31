package com.learn.Proxy.cglib;

import com.learn.Proxy.jdk.Person;
import com.learn.Proxy.jdk.PersonImpl;
import org.springframework.cglib.proxy.Enhancer;


public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setAge(23);
        user.setName("hongtaolong");
        user.setPassword("hong");
        //被代理的对象
        Person delegate = new PersonImpl();
        UserServiceCglibInterceptor serviceInterceptor = new UserServiceCglibInterceptor(delegate);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(delegate.getClass());
        enhancer.setCallback(serviceInterceptor);
        //动态代理类
        PersonImpl cglibProxy = (PersonImpl)enhancer.create();
        System.out.println("动态代理类父类："+cglibProxy.getClass().getSuperclass());
        cglibProxy.addUser(user);
    }
}
