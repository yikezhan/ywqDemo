package com.yuwuquan.demo.common.multhreadtest;

/**
 *      1、一般而言，可能有三种原因引起阻塞：等待阻塞、同步阻塞以及其他阻塞（睡眠、jion或者IO阻塞）;
 *      对于Java而言:
 *      ①等待阻塞是调用wait方法产生的
 *      ②同步阻塞则是由同步块（synchronized）产生的
 *      ③，睡眠阻塞是由sleep产生的
 *      ④jion阻塞是由jion方法产生的。
 *      2、interrupt()、interrupted()、isInterrupt()方法
 *      ①、进程调用interrupt()方法后，会置线程的中断标志位为false。
 *      对于正在阻塞的线程会抛出InterruptedException异常。
 *      对于非阻塞的线程会继续执行，如果之后调用wait()、join()、sleep()时也会抛出InterruptedException异常。
 *      ②、interrupted()方法和isInterrupt()方法类似，区别在于前者在执行后会重新置中断位为true，后者不会。
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while(1 == 1){
                    System.out.println(num++);
                    /**
                     * 分别打开下面的代码体会下interrupted()和isInterrupted()的区别。
                     * 1、interrupted()会检测到中断位后清除中断位信息，
                     * 连续调用时，第二次返回就是false了。（名字起的有问题，和isInterrupted才是一类的方法好吧）
                     * 2、isInterrupted()只是检测是否标志了中断位，不会做清除。
                     */
//                    if(Thread.currentThread().interrupted()){
//                        System.out.println("被外部中断");
//                    }
//                    if(Thread.currentThread().isInterrupted()){
//                        System.out.println("被外部中断");
//                    }
                }
            }
        });
        thread.start();
        Thread.currentThread().sleep(300);
        /**
         * 设置中断位，进程被调用该方法时不会影响进程原有的执行，但是sleep、wait、join等阻塞方法会抛出异常。
         */
        thread.interrupt();
    }
}
