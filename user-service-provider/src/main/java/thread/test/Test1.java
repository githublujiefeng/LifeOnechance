package thread.test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * synchronized 和ReentrantLock 都是可重入锁
 * 可中断锁 (interrupt()不能中断在运行中的线程，它只能改变中断状态而已。)
 * 公平锁
 * 读写锁
 */

public class Test1 {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    public static void main(String[] args) {
//        final Test1 test1 = new Test1();
//        new Thread(){
//          @Override
//          public void run() {
//              test1.insert(Thread.currentThread());
//          }
//        }.start();
//        new Thread(){
//            @Override
//            public void run(){
//                test1.insert(Thread.currentThread());
//            }
//        }.start();
        Test1 test1 = new Test1();
        MyThreadq thread1 = new MyThreadq(test1);
        MyThreadq thread2 = new MyThreadq(test1);
        thread1.start();
        thread2.start();
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
       /* Test1 test1 = new Test1();
        new Thread(){
            public void run(){
                test1.get(Thread.currentThread());
            }
        }.start();
        new Thread(){
            public void run(){
                test1.get(Thread.currentThread());
            }
        }.start();*/

    }

    /**
     * ReentrantLock
     * @param thread
     */
    public void insert(Thread thread){
        //lock.lock();
        if(lock.tryLock()) {
            try {
                System.out.println(thread.getName() + "获取锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                lock.unlock();
                System.out.println(thread.getName() + "释放锁");
            }
        }else{
            System.out.println(thread.getName() + "获取不到锁");
        }
    }

    /**
     * 中断锁的应用
     */
    public void insertInterrupt(Thread thread) throws InterruptedException {
        lock.lockInterruptibly();////注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {

            System.out.println(thread.getName()+"获取锁");
            long startTime =System.currentTimeMillis();
            for(    ;     ;) {
                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                    break;
                //插入数据
            }
            //Thread.sleep(10000);
        }finally {
            System.out.println(Thread.currentThread().getName()+"执行finally");
            lock.unlock();
            System.out.println(thread.getName()+"释放锁");
        }
    }

    /**
     * 读写锁
     */
    public void get(Thread thread){
        rwl.readLock().lock();
        try{

            long start= System.currentTimeMillis();
            while (System.currentTimeMillis()-start<=1){
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(thread.getName()+"读操作完毕");
        }finally {
            rwl.readLock().unlock();
        }
    }
}

class MyThreadq extends Thread{
    private Test1 test1 = null;
    public MyThreadq(Test1 test1){
        this.test1 = test1;
    }
    @Override
    public void run(){
        try{
            test1.insertInterrupt(Thread.currentThread());
            //test1.insert(Thread.currentThread());
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName()+"被中断");
        }
    }
}