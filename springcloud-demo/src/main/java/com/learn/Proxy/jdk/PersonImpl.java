package com.learn.Proxy.jdk;

import com.learn.Proxy.cglib.User;

public class PersonImpl implements Person {
    @Override
    public void checkName() {
        System.out.println("校验姓名！");
    }


    public void addUser(User user) {
        System.out.println("jdk...正在注册用户，用户信息为："+user);
    }

    final public void hello(){};

}
