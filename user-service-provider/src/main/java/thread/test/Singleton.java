package thread.test;

import java.util.concurrent.locks.ReentrantLock;

public class Singleton {
    static volatile Singleton instance;

    static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    String name = "ljf";

    public static void main1(String[] args) {
        Singleton singleton = new Singleton();
        new Thread() {
            public void run() {
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

    void test() {
        synchronized (Singleton.class) {
            try {
                wait();
                notifyAll();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
//        SafeCalc sf = new SafeCalc();
//        new Thread(){
//            public void run(){
//                sf.addOne();
//                try {
//                    Thread.sleep(100);
//                    System.out.println(sf.get());
//                }catch (InterruptedException e){
//
//                }
//            }
//        }.start();
//        new Thread(){
//            public void run(){
//
//                sf.addOne();
//                System.out.println(sf.get());
//            }
//        }.start();
        Thread thread1= new Thread(){
            public void run(){
                Thread th = Thread.currentThread();
                while (true) {
                    if (th.isInterrupted()) {
                        System.out.println("isOK?");
                        break;
                    } // 省略业务代码无数
                    try {
                        System.out.println("I'm coming");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("err");
        }
        thread1.interrupt();
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
