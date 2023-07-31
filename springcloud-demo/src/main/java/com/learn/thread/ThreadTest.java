package com.learn.thread;

import java.util.Random;

// 1。 有二十个账户。每个账户初始余额10000元。
// 2。 有十个转账线程，对二十个账户中的两个随机选取账户进行转账，转账额度100以内正整数随机数。
// 3。 每个线程执行100次转账操作。
// 4。 最后请打印出二十个账户的余额
public class ThreadTest {
    public static void main(String[] args) {
        for(int i=0;i<1;i++) {
            int res = new ThreadTest().doSomething();
            if(res!=200000){
                System.out.println("“”错误："+res);
                break;
            }
        }
    }

    int doSomething(){
        Account[] accounts = new Account[20];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(10000);

        }
        for (int i= 0;i<10;i++){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    int num = 100;
                    while (num-->0) {
                        Random rondom = new Random();
                        int first = rondom.nextInt(20);
                        int send = rondom.nextInt(20);
                        while (first == send) {
                            send = rondom.nextInt(20);
                        }
                        System.out.println(Thread.currentThread().getName()+":次数 "+num);
                        synchronized (accounts[first]){
                            synchronized (accounts[send]) {
                                accounts[first].doTran(accounts[send]);
                            }
                        }

                    }
                }
            };
            thread.setName("线程："+i);
            thread.start();

        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sum =0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(accounts[i].money);
            sum +=accounts[i].money;
        }
        System.out.println("total:"+ sum);
        return sum;
    }

}

class Account{
    Integer money;

    public Account(Integer money){
        this.money = money;
    }
    void doTran( Account b){
        Random rondom = new Random();
        int pay = rondom.nextInt(100) + 1;
        if (pay > this.money) {
            System.out.println("余额不足，无法转帐！");
            return;
        }
//        synchronized (Account.class) {
            this.money = this.money - pay;
            b.money = b.money + pay;
//        }
    }

}
