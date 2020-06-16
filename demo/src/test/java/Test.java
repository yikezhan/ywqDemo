import com.alibaba.fastjson.JSONObject;
import com.yuwuquan.demo.util.common.StringUtil;
import lombok.Getter;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Test implements Runnable{
    ThreadLocal<Integer> i = new ThreadLocal<>();
    Integer k = 0;
    AtomicInteger j = new AtomicInteger(1);
    @Override
    public void run() {
        j.getAndIncrement();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(k);
    }

    public static void main(String[] args) {
        Test t = new Test();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
    }
}
