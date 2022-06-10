package com.learn.Proxy;

public class PersonImpl implements Person {
    @Override
    public void checkName() {
        System.out.println("校验姓名！");
    }
    final public void hello(){};
}
