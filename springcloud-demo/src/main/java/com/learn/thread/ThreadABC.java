package com.learn.thread;

public class ThreadABC {
    private boolean printA = true;
    private boolean printN = false;
    private boolean printT = false;

    static class runThreadA extends Thread {
        private ThreadABC myThread;

        public runThreadA(ThreadABC myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintA();
        }
    }

    static class runThreadN extends Thread {
        private ThreadABC myThread;

        public runThreadN(ThreadABC myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintN();
        }
    }

    static class runThreadT extends Thread {
        private ThreadABC myThread;

        public runThreadT(ThreadABC myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintT();
        }
    }

    public static void main(String[] args) {
        ThreadABC my = new ThreadABC();
        Thread thread1 = new runThreadA(my);
        thread1.start();
        Thread thread2 = new runThreadN(my);
        thread2.start();
        Thread thread3 = new runThreadT(my);
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void myPrintA() {
        while (printA) {
                System.out.print("a");
                printA = false;
                printN = true;
                printT = false;
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    System.out.print("a");
                    printA = false;
                    printN = true;
                    printT = false;
                    notifyAll();
                }
            }
        notifyAll();
    }

    synchronized void myPrintN() {
        while (printN) {
                System.out.print("n");
                printA = false;
                printN = false;
                printT = true;
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    System.out.print("n");
                    printA = false;
                    printN = false;
                    printT = true;
                    notifyAll();
                }

            }
        notifyAll();
    }

    synchronized void myPrintT() {
        while (printT) {
                System.out.print("t");
                printA = true;
                printN = false;
                printT = false;
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    System.out.print("t");
                    printA = true;
                    printN = false;
                    printT = false;
                    notifyAll();
                }
        }
        notifyAll();
    }

}
