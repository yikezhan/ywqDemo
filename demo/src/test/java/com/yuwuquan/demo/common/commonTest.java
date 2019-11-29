package com.yuwuquan.demo.common;

import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;
import com.yuwuquan.demo.util.multithreading.ExecutorServiceFactory;

import java.util.HashMap;
import java.util.concurrent.*;

public class commonTest {
    private static int num = 0;
    public static void main(String[] args) throws Exception{
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        ExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory(80, 100, 100);
        ExecutorService executorService = executorServiceFactory.getObject();
        for(int i=0; i<50; i++){
            num = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<=num;i++){
                        hashMap.put(1,i);
                        concurrentHashMap.put(1,i);
                    }
                }
            });
        }
        Thread.sleep(5000);
        System.out.println(hashMap.get(1));//暂时没有测出来hashmap的不安全，下次测
        System.out.println(concurrentHashMap.get(1));
    }
}
