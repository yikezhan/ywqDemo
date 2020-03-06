package com.yuwuquan.demo.common.waitnotifytest;


public class WaitNotifyTest {
    private static int num = 0;
    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();
    public static void main(String[] args){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(num<200){//注：在这里打个断点停留会就会出问题，不算是真正的交替打印吧。这个程序只是为了测试wait和notify方法。
                    synchronized (lock1){
                        try {
                            lock1.wait();//使用wait后会立即释放锁，另一个代码块的synchronized(lock1)就能进去了。
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("线程1:" + num);
                        num ++;
                        synchronized (lock2){//wait释放后即可获得lock2锁了
                            lock2.notify();//唤醒lock2.wait()处的代码
                        }
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                    while(num<200){
                        synchronized (lock2){
                            try {
                                lock2.wait(10000);//参数表示10s钟后自动
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("线程2:" + num);
                            num ++;
                            synchronized (lock1){//wait释放后即可获得lock1的锁了
                                lock1.notify();//唤醒一个lock1.wait()处的代码
                            }
                        }
                    }
            }
        });
        thread.start();
        thread2.start();
    }
}
