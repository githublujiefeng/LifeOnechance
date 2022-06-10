package com.learn.thread;

public class Singleton {

    static volatile Singleton instance;
    static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null){
                    instance =new Singleton();
                }
            }
        }
        return instance;
    }
    String name = "ljf";
    public static void main1(String[] args) {
        Singleton singleton = new Singleton();
        new Thread(){
            public void run(){
                System.out.println(singleton.name);
                singleton.name = "qqq";
//                try {
//                    Thread.sleep(1000);
//                    System.out.println(singleton.name);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }.start();
        System.out.println(singleton.name);
        singleton.name = "hhh";
        System.out.println(singleton.name);
    }

     void test(){
        synchronized(Singleton.class){
              try{
                  wait();
                  notifyAll();
              }catch(InterruptedException e){

              }
        }
    }

    public static void main(String[] args) {
        SafeCalc sf = new SafeCalc();
        new Thread(){
            public void run(){
                sf.addOne();
                try {
                    Thread.sleep(100);
                    System.out.println(sf.get());
                }catch (InterruptedException e){

                }
            }
        }.start();
        new Thread(){
            public void run(){

                    sf.addOne();
                System.out.println(sf.get());
            }
        }.start();
    }
}

class SafeCalc {
    long value = 0L;
    synchronized long get() {
        return value;
    }
    synchronized void addOne() {
        value += 1;
    }
}