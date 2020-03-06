package com.yuwuquan.demo.common.multhreadtest.reentrantlocktest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示newCondition的用法。newCondition、await、signal的用法。和Object的那几个方法一样
 */
public class ReentrantLockTest2 {
    private class MyRun implements Runnable{
        private ReentrantLock lock1 = new ReentrantLock();
        private Condition condition = lock1.newCondition();
        private int num = 0;
        private ThreadLocal<Integer> tmp = new ThreadLocal<>();
        @Override
        public void run() {
            lock1.lock();
            try {
                System.out.println(num + "获取到了lock1");
                tmp.set(num);
                if(num == 0){
                    num ++;
                    condition.await();
                }else{
                    condition.signal();
                    condition.await();
                }
                System.out.println("线程"+tmp.get()+"被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signal();
            lock1.unlock();
        }
    }
    private void test1(){
        Runnable runnable = new MyRun();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        /**输出：
         * 0获取到了lock1
         * 1获取到了lock1
         * 线程0被唤醒
         * 线程1被唤醒
         */
    }
    public static void main(String[] args) {
        ReentrantLockTest2 reentrantLockTest = new ReentrantLockTest2();
        reentrantLockTest.test1();
    }
}
