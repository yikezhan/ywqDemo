package com.yuwuquan.demo.common.multhreadtest.reentrantlocktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示死锁，以及lock、tryLock、unlock的用法。
 */
public class ReentrantLockTest {
    private ReentrantLock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private class MyRun implements Runnable{
        @Override
        public void run() {
            try{
                while(!lock1.tryLock(2,TimeUnit.SECONDS)){//两秒内都没获取到则休眠10秒
                    Thread.sleep(10000);//如果没有获得则休眠10秒
                }
                Thread.sleep(5000);
                System.out.println("获取到lock1，申请lock2");
                lock2.lock();
                System.out.println("获取到lock2");
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                lock2.unlock();
                lock1.unlock();
            }
            System.out.println("ss1");
        }
    }
    private class MyRun2 implements Runnable{
        @Override
        public void run() {
            try{
                lock2.lock();
                Thread.sleep(5000);
                System.out.println("获取到lock2，申请lock1");
                lock1.lock();
                System.out.println("获取到lock1");
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
            System.out.println("ss2");
        }
    }
    private void test1() {
        Runnable runnable = new MyRun();
        Runnable runnable2 = new MyRun2();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.test1();
    }
}
