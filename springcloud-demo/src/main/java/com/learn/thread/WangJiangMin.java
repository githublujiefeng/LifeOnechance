package com.learn.thread;

public class WangJiangMin {
    //新方案 同时只有一个线程在执行

    private Object object = new Object();
    public volatile int start = 0;

    public static void main(String[] args) {
        // 3是顺序阻塞 超过3阻塞就乱了
        new WangJiangMin().buildThread(5);
    }

    public void buildThread(int size) {
        for (int i = 0; i < size; i++) {
            int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        synchronized (object) {
                            if (start != finalI) {
                                //System.out.println("thread:"+finalI+"_wait");
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (start == size - 1) {
                                    start = 0;
                                } else {
                                    start++;
                                }
                                System.out.println( currentThread().getName() + "_run");
//                                try {
//                                    Thread.sleep(400);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                                object.notifyAll();
                            }
                        }
                    }
                }

            }.start();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

}
