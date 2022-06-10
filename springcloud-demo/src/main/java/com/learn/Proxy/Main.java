package com.learn.Proxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        SMSService smsService = new SMSServiceImpl();
        smsService = (SMSService)Proxy.newProxyInstance(Main.class.getClassLoader(),
                new Class[]{Person.class,SMSService.class},
                new MoneyCountInvocationHandler(smsService));

        smsService.sendMessage();
    }
}
