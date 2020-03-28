package com.yuwuquan.demo.common.leetcode;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

//使用信号量机制实现
public class ZeroEvenOdd2 {
        private volatile int n;
        private volatile int x=1;
        private volatile int num;
        private Semaphore semaphore1 = new Semaphore(1);//zero
        private Semaphore semaphore2 = new Semaphore(0);//even
        private Semaphore semaphore3 = new Semaphore(0);//odd
        public ZeroEvenOdd2(int n) {
            this.n = n;
            this.num = n*2;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while(true){
                semaphore1.acquire();
                if(num>0){
                    printNumber.accept(0);
                    num--;
                    if(x % 2 == 0){
                        semaphore2.release();
                    }else{
                        semaphore3.release();
                    }
                }else{
                    semaphore3.release();
                    semaphore2.release();
                    break;
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while(true){
                semaphore2.acquire();
                if(num>0){
                    num--;
                    printNumber.accept(x++);
                    semaphore1.release();
                }else{
                    semaphore1.release();
                    semaphore3.release();
                    break;
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            while(true){
                semaphore3.acquire();
                if(num>0){
                    num--;
                    printNumber.accept(x++);
                    semaphore1.release();
                }else{
                    semaphore1.release();
                    semaphore2.release();
                    break;
                }
            }
        }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            private volatile int i = 0;
            ZeroEvenOdd2 zeroEvenOdd = new ZeroEvenOdd2(2);
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