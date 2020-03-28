package com.yuwuquan.demo.common.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class ZeroEvenOdd1 {
    private int n;
    private int current = 1;
    private final Lock lock = new ReentrantLock();
    private final Condition evenCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();
    private final Condition zeroCondition = lock.newCondition();
    // 0 refers zero, 1 refers odd, 2 refers even
    private int state = 0;

    public ZeroEvenOdd1(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != 0) {
                    zeroCondition.await();
                }
                printNumber.accept(0);
                if ((current & 1) == 0) {
                    state = 2;
                    evenCondition.signal();
                } else {
                    state = 1;
                    oddCondition.signal();
                }
                zeroCondition.await();
            }
            oddCondition.signal();
            evenCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    // 偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != 2) {
                    evenCondition.await();
                } else {
                    printNumber.accept(current++);
                    state = 0;
                    zeroCondition.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // 奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (current <= n) {
                if (state != 1) {
                    oddCondition.await();
                } else {
                    printNumber.accept(current++);
                    state = 0;
                    zeroCondition.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
