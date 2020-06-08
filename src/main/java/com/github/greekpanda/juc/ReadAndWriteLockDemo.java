package com.github.greekpanda.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 4:01
 */
public class ReadAndWriteLockDemo {
    public static void main(String[] args) {
        ReadAndWriteLockSub rwd = new ReadAndWriteLockSub();
        new Thread(() -> rwd.write((int) (Math.random() * 100)), "Write: ").start();

        for (int i = 0; i < 10; i++) {
            new Thread(rwd::read, "read: ").start();
        }
    }
}

class ReadAndWriteLockSub {
    private int num = 0;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //读
    public void read() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " " + num);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    //写
    public void write(int num) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " " + num);
            this.num = num;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}