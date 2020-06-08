package com.github.greekpanda.juc;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * * 一、用于解决多线程安全问题的方式：
 *  *
 *  * synchronized:隐式锁
 *  * 1. 同步代码块
 *  *
 *  * 2. 同步方法
 *  *
 *  * jdk 1.5 后：
 *  * 3. 同步锁 Lock
 *  * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 3:08
 */
public class TestLock {
    @Test
    public void test() {
        LockDemo lockDemo = new LockDemo();
        new Thread(lockDemo, "1111").start();
        new Thread(lockDemo, "2222").start();
        new Thread(lockDemo, "3333").start();
        new Thread(lockDemo, "4444").start();

    }
}

class LockDemo implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();

            System.out.println("11111111111111111111");
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    --tick;
                    System.out.println("current thread name: " + Thread.currentThread().getName());
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
