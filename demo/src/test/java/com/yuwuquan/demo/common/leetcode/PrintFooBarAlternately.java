package com.yuwuquan.demo.common.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintFooBarAlternately {
    private int n;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private volatile boolean flag = false;
    public PrintFooBarAlternately(int n) {
        this.n = n;
    }
    public void foo() throws InterruptedException {

//        for (int i = 0; i < n; i++) {
//            lock.lock();
//            if(flag)
//                condition.await();
//            flag = true;
//            // printFoo.run() outputs "foo". Do not change or remove this line.
//            System.out.println("foo");
//            condition.signal();
//            lock.unlock();
//        }
        for (int i = 0; i < n; i++) {
            while(true){
                if(!flag) break;
            }
            System.out.println("foo");
            flag = true;
        }
    }

    public void bar() throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while(true){
                    if(flag) break;
                }
                System.out.println("bar");
                flag = false;
            }
//        for (int i = 0; i < n; i++) {
//            lock.lock();
//            if(!flag)
//                condition.await();
//            flag = false;
//            // printBar.run() outputs "bar". Do not change or remove this line.
//            System.out.println("bar");
//            condition.signal();
//            lock.unlock();
//        }
    }
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            PrintFooBarAlternately printFooBarAlternately = new PrintFooBarAlternately(2);
            public volatile  boolean flag = true;
            @Override
            public void run() {
                try {
                        if(flag){
                            flag = !flag;
                            printFooBarAlternately.bar();
                        }else{
                            flag = !flag;
                            printFooBarAlternately.foo();
                        }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
