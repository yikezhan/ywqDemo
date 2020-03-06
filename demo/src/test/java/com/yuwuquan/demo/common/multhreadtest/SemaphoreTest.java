package com.yuwuquan.demo.common.multhreadtest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量机制
 */
public class SemaphoreTest {
    private class MyRun implements Runnable{
        private Semaphore semaphore = new Semaphore(3);//初始共享资源为3，默认非公平锁
        private volatile AtomicInteger atomicInteger = new AtomicInteger(0);
        @Override
        public void run() {
            try {
                semaphore.acquire();//获取1个共享资源。（也可以传参获取n个共享资源）
//                semaphore.tryAcquire();
                System.out.println("线程"+atomicInteger.getAndIncrement());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                semaphore.release();//释放1个共享资源。（也可以传参释放n个共享资源）
        }
    }
    public void test(){
        MyRun myRun = new MyRun();
        for(int i=0;i<10;i++){
            Thread thread1 = new Thread(myRun);
            thread1.start();
        }
    }
    public static void main(String[] args) {
        (new SemaphoreTest()).test();
    }
}
