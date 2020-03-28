package com.yuwuquan.demo.common.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

//使用Condition+Lock实现
public class ZeroEvenOdd {
    private volatile int n;
    private volatile int x=1;
    private volatile int flag = 1;
    private volatile int num = 0;
        private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();//zero需要获取
    private Condition condition2 = lock.newCondition();//even需要获取
    private Condition condition3 = lock.newCondition();//odd需要获取
    public ZeroEvenOdd(int n) {
        this.n = n;
        this.num = n*2;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        System.out.println("zero start");
        while(true){
            lock.lock();
            System.out.println("zero，flag="+flag);
            if(flag != 1)
                condition1.await();
            if(num>0){
                num--;
                printNumber.accept(0);
                if(x % 2 == 0){
                    flag = 2;
                    condition2.signal();
                }else{
                    flag = 3;
                    condition3.signal();
                }
            }else {
                condition2.signal();
                condition3.signal();
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        System.out.println("even start");
        while(true) {
            lock.lock();
            System.out.println("even，flag="+flag);
            if(flag != 2){
                condition2.await();
            }
            if(num>0){
                printNumber.accept(x++);
                num--;
                flag = 1;
                condition1.signal();
            }else{
                condition1.signal();
                condition3.signal();
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        System.out.println("odd start");
        while(true){
            lock.lock();
            System.out.println("odd，flag="+flag);
            if(flag != 3){
                condition3.await();
            }
            if(num>0){
                printNumber.accept(x++);
                num--;
                flag = 1;
                condition1.signal();
            }else{
                condition2.signal();
                condition1.signal();
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            private volatile int i = 0;
            ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);
            IntConsumer intConsumer = new IntConsumer() {
                @Override
                public void accept(int value) {
                    System.out.println(value);
                }
            };
            @Override
            public void run() {
                try {

                    switch (i){
                        case 0:
                            i++;
                            zeroEvenOdd.zero(intConsumer);break;
                        case 1:
                            i++;
                            zeroEvenOdd.odd(intConsumer);break;
                        case 2:
                            i++;
                            zeroEvenOdd.even(intConsumer);break;
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable,"线程a");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(runnable,"线程b");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread3 = new Thread(runnable,"线程c");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}