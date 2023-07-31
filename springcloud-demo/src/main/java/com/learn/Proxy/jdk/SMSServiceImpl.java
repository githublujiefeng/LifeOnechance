package com.learn.Proxy.jdk;

public class SMSServiceImpl implements SMSService {
    @Override
    public void sendMessage() {
        System.out.println("你正在尝试学习代理");
    }
}
