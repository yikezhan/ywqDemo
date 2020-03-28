package com.yuwuquan.demo.common;

import java.util.concurrent.Semaphore;

public class CommonTest {
    public static void main(String[] args) {
        System.out.println("ai");
        Runnable runnable = new Runnable() {
            Semaphore semaphore = new Semaphore(1);
            volatile int i = 1;
            @Override
            public void run() {
                try {
                    semaphore.acquire(1);
                    if(i % 2 == 0){
                        System.out.println("a");
                    }else{
                        System.out.println("b");
                    }
                    i++;
                    semaphore.release(1);
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
