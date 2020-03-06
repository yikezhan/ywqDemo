package com.yuwuquan.demo.common.multhreadtest.reentrantlocktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示lock()、lockInterruptibly()、tryLock()的区别
 */
public class ReentrantLockTest3 {
    private ReentrantLock lock1 = new ReentrantLock();
    private class MyRun implements Runnable{
        @Override
        public void run() {
            try {
//                lock1.lock();//不可被中断
//                lock1.lockInterruptibly();//可以被外部的interrupt中断
                lock1.tryLock(100, TimeUnit.SECONDS);//可以被外部的interrupt中断
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("获取到了lock1");
            lock1.unlock();
        }
    }
    private void test1(){
        lock1.lock();
        Runnable runnable = new MyRun();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();//主线程中中断thread1线程
    }
    public static void main(String[] args) {
        ReentrantLockTest3 reentrantLockTest = new ReentrantLockTest3();
        reentrantLockTest.test1();
    }
}
