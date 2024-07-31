package com.learn.thread;

public class ThreadByABC {
    volatile static Integer num = 1;
    static final Integer max = 100;
    public static void main(String[] args) {
//        Thread thradA = new Thread(()->{
//            while(num%3!=1) {
//                wait();
//            }
//            System.out.print(show[0]);
//        },"A");
//        Thread thradB = new Thread(()->{
//            while(num<max && num%show.length==2) {
//                num++;
//                System.out.print(show[1]);
//            }
//           // System.out.print("B");
//        },"B");
//        Thread thradC = new Thread(()->{
//            while(num<max && num%show.length==3) {
//                num++;
//                System.out.print(show[2]);
//            }
//            //System.out.print("C");
//        },"C");
//        thradA.start();
//        thradC.start();
//        thradB.start();
    }

    Thread creatThread(String threadName, String print){
        return new Thread(()->{
            System.out.print(print);
        },threadName);
    }
}
