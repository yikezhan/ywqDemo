package com.yuwuquan.demo.common;

import java.util.concurrent.atomic.AtomicInteger;

class Thread1 extends Thread{
    @Override
    public void run(){
        while (1==1){
            if(CommonTest.signal.intValue() % 2 == 0){
                synchronized (CommonTest.signal){
                    System.out.println("a");
                    CommonTest.signal.getAndIncrement();
                }
            }
        }
    }
}
class Thread2 extends Thread{
    @Override
    public void run(){
        while (1==1){
            if(CommonTest.signal.intValue() % 2 == 1){
                synchronized (CommonTest.signal){
                    System.out.println("b");
                    CommonTest.signal.getAndIncrement();
                }
            }
        }
    }
}

/**
 * 依次打印两线程的数据
 */

public class CommonTest{
    protected static AtomicInteger signal = new AtomicInteger(0);
    private static int num = 0;
    public static void main(String[] args) throws Exception{

        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
//        HashMap<Integer,Integer> hashMap = new HashMap<>();
//        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
//        ExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory(80, 100, 100);
//        ExecutorService executorService = executorServiceFactory.getObject();
//        AtomicInteger  i = new AtomicInteger(1);
//        for( ; i.intValue()<5; i.getAndAdd(1)){
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    Lock lock = new NonReentrantLock();
//                    synchronized (this.getClass()){
//                        System.out.println("hah" + i);
//                        for(int j=0;j<=i.intValue();j++){
//                            hashMap.put(1,j);
//                            lock.lock();
//                            try{
//                                Thread.sleep(1000);
//                            }catch (Exception e){
//
//                            }
//                            concurrentHashMap.put(1,j);
//                            System.out.println("haha" + j);
//                            lock.unlock();
//                        }
//                    }
//
//
////                    for(int i=0;i<=num;i++){
////
////                    }
//                }
//            });
//            Thread.sleep(1000);
//        }
////        System.out.println(hashMap.get(1));//暂时没有测出来hashmap的不安全，下次测
////        System.out.println(concurrentHashMap.get(1));
    }
}
