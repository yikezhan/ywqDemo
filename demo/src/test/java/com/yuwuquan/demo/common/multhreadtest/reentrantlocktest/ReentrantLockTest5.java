package com.yuwuquan.demo.common.multhreadtest.reentrantlocktest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock中的其他一些方法的作用。
 */
public class ReentrantLockTest5 {
    private ReentrantLock lock1 = new ReentrantLock();
    private Condition condition = lock1.newCondition();
    private class MyRun implements Runnable{
        @Override
        public void run() {
            lock1.lock();
            lock1.lock();
            System.out.println("当前线程保持的锁数量："+lock1.getHoldCount());//2
//            lock1.getWaitQueueLength();
            System.out.println("是否有线程正在等待与此锁有关的给定条件："+lock1.hasWaiters(condition));//true(主线程在wait)
            System.out.println("与此锁相关的给定条件的线程数："+lock1.getWaitQueueLength(condition));//1
            condition.signal();
            try {
                condition.await();//释放锁，中断当前线程，继续主线程部分的执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("是否有线程正在等待与此锁有关的给定条件："+lock1.hasWaiters(condition));//false(主线程已跑完)
            System.out.println("与此锁相关的给定条件的线程数："+lock1.getWaitQueueLength(condition));//0
            lock1.unlock();
            lock1.unlock();
        }
    }
    private void test1() throws InterruptedException {
        lock1.lock();
        Runnable runnable = new MyRun();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread.sleep(1000);
        System.out.println("正在获取此锁的线程个数:"+lock1.getQueueLength());//1
        System.out.println("是否有线程正在等待获取此锁:"+lock1.hasQueuedThreads());//true
        System.out.println("thread1正在获取此锁:"+lock1.hasQueuedThread(thread1));//true
        condition.await();
        condition.signal();
        lock1.unlock();
        /**
         * 输出：
         * 正在获取此锁的线程个数:1
         * 是否有线程正在等待获取此锁:true
         * thread1正在获取此锁:true
         * 当前线程保持的锁数量：2
         * 是否有线程正在等待与此锁有关的给定条件：true
         * 与此锁相关的给定条件的线程数：1
         * 是否有线程正在等待与此锁有关的给定条件：false
         * 与此锁相关的给定条件的线程数：0
         */
    }
    public static void main(String[] args) {
        ReentrantLockTest5 reentrantLockTest = new ReentrantLockTest5();
        try {
            reentrantLockTest.test1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
