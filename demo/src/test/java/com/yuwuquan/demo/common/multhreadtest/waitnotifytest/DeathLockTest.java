package com.yuwuquan.demo.common.multhreadtest.waitnotifytest;

public class DeathLockTest {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程1获取到锁lock1，申请lock2");
                    synchronized (lock2) {
                        System.out.println("ok1");
                    }
                }

            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程2获取到锁lock2，申请lock1");
                    synchronized (lock1) {
                        System.out.println("ok2");
                    }
                }

            }
        });
        thread.start();
        thread2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
