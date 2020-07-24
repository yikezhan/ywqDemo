package com.yuwuquan.demo.common.multhreadtest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1、从输出可以看出，同一个对象的ThreadLocal在不同线程中各有自己的一份值，
 * 说白了ThreadLocal就是一个工具类，为一个map结构，key为线程名，value为值。
 * 2、一般这种变量都是竞争的资源变量，可以考虑两种处理方式，
 * ①使用synchronized，对这部分资源做限制一个进程进入。
 * ②使用ThreadLocal<T>作为线程变量，每个线程各有一份，一般如果作为线程间的控制作用的话就不适用了。
 * 3、这里val和integerThreadLocal表示类对象中属性的两种作用的场景，
 * 前者为线程间操作共享，后者为线程间各种独立的副本。
 *
 */
public class ThreadLocalTest {
    private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>();
    private class MyRun implements Runnable{
        private int val=0;
        @Override
        public void run() {//注意比较i和integerThreadLocal打印值的区别
            System.out.println(val);
            System.out.println(integerThreadLocal.get());
            integerThreadLocal.set(3);
            val++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest t = new ThreadLocalTest();
        t.test1();//0     null    1       null
        System.out.println("-------------------");
        t.test2();//0     null    0       null
        System.out.println("-------------------");
        t.test3();//0     null    0       null

    }
    private void test1() throws InterruptedException {
        Runnable runnable = new MyRun();//正常在启动线程时，只会new一个Runnable对象，因为内部数据都在一个对象内，如test2那样new多个无意义。
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        thread.start();
        thread.join();
        thread1.start();
        thread1.join();
    }
    private void test2() throws InterruptedException {
        Runnable runnable = new MyRun();
        Runnable runnable1 = new MyRun();
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable1);//这样new一个新的话就意义不大了，因为多线程之间可能会协作处理共同的资源
        thread.start();
        thread.join();
        thread1.start();
        thread1.join();
    }
    private void test3() throws InterruptedException {
        Runnable runnable = new MyRun();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);//这样new一个新的话就意义不大了，因为多线程之间可能会协作处理共同的资源
        executorService.submit(thread);
        thread.join();
        executorService.submit(thread1);
        thread1.join();
    }
}


