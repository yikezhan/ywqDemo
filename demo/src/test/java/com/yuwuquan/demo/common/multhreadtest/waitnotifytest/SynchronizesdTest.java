package com.yuwuquan.demo.common.multhreadtest.waitnotifytest;


public class SynchronizesdTest {
    private final static Object lock = new Object();
    public static void main(String[] args){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    synchronized (lock){
                        System.out.println("线程1");
                        try {
                            Thread.currentThread().sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //和以前的理解一致。以下代码需要线程1的同步代码块结束后才能执行
                synchronized (lock){
                    System.out.println("线程2");
                }
            }
        });
        thread.start();
        thread2.start();
        //join操作，使主线程需要等待thread和thread2结束后才会继续执行
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");//使用了join后，保证end输出会在最后
    }
}
