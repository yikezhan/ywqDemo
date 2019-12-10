package com.yuwuquan.demo.common;

import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;
import com.yuwuquan.demo.util.multithreading.ExecutorServiceFactory;
import org.jboss.netty.util.internal.NonReentrantLock;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class commonTest {
    private static int num = 0;
    public static void main(String[] args) throws Exception{
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        ExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory(80, 100, 100);
        ExecutorService executorService = executorServiceFactory.getObject();
        AtomicInteger  i = new AtomicInteger(1);
        for( ; i.intValue()<5; i.getAndAdd(1)){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Lock lock = new NonReentrantLock();
                    synchronized (this.getClass()){
                        System.out.println("hah" + i);
                        for(int j=0;j<=i.intValue();j++){
                            hashMap.put(1,j);
                            lock.lock();
                            try{
                                Thread.sleep(1000);
                            }catch (Exception e){

                            }
                            concurrentHashMap.put(1,j);
                            System.out.println("haha" + j);
                            lock.unlock();
                        }
                    }


//                    for(int i=0;i<=num;i++){
//
//                    }
                }
            });
            Thread.sleep(1000);
        }
//        System.out.println(hashMap.get(1));//暂时没有测出来hashmap的不安全，下次测
//        System.out.println(concurrentHashMap.get(1));
    }
}
