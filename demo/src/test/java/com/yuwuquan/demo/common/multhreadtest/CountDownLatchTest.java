package com.yuwuquan.demo.common.multhreadtest;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(2);
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1");
                countDownLatch.countDown();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2");
                countDownLatch.countDown();
            }
        });
        thread.start();
        thread2.start();
        System.out.println("当前count数量："+countDownLatch.getCount());
        try {
                countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程继续执行");
    }
}
//当前数量：2
//线程2
//线程1
//主线程