package thread.test.bishi;

public class MyThread {
    private boolean printA = true;
    private boolean printN = false;
    private boolean printT = false;

    static class runThreadA extends Thread {
        private MyThread myThread;

        public runThreadA(MyThread myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintA();
        }
    }

    static class runThreadN extends Thread {
        private MyThread myThread;

        public runThreadN(MyThread myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintN();
        }
    }

    static class runThreadT extends Thread {
        private MyThread myThread;

        public runThreadT(MyThread myThread) {
            this.myThread = myThread;
        }

        public void run() {
            myThread.myPrintT();
        }
    }

    public static void main(String[] args) {
        MyThread my = new MyThread();
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
            try {
                System.out.print("a");
                printA = false;
                printN = true;
                printT = false;
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }finally {
                notifyAll();
            }
        }
    }

    synchronized void myPrintN() {
        while (printN) {
            try {
                System.out.print("n");
                printA = false;
                printN = false;
                printT = true;
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }finally {
                notifyAll();
            }
        }
    }

    synchronized void myPrintT() {
        while (printT) {
            try {
                System.out.print("t");
                printA = true;
                printN = false;
                printT = false;
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }finally {
                notifyAll();
            }
        }
    }
}
