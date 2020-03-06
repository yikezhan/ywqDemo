package com.yuwuquan.demo.common.multhreadtest.reentrantlocktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示isLocked、isHeldByCurrentThread()、isFair()的功能
 */
public class ReentrantLockTest4 {
    private ReentrantLock lock1 = new ReentrantLock();
    private class MyRun implements Runnable{
        @Override
        public void run() {
            System.out.println("lock1锁"+(lock1.isFair()? "" : "不")+"是公平锁");
            if(lock1.isLocked()){
                System.out.println("lock1锁被某一线程占用");
                if(lock1.isHeldByCurrentThread()){
                    System.out.println("lock1锁被当前线程占用");
                }else{
                    System.out.println("lock1锁被非当前线程占用");
                }
            }

        }
    }
    private void test1(){
        lock1.lock();
        Runnable runnable = new MyRun();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        //输出：
        // lock1锁不是公平锁
        //lock1锁被某一线程占用
        //lock1锁被非当前线程占用
    }
    public static void main(String[] args) {
        ReentrantLockTest4 reentrantLockTest = new ReentrantLockTest4();
        reentrantLockTest.test1();
    }
}
