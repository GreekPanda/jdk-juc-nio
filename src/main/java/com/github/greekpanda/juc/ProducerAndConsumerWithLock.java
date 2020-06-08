package com.github.greekpanda.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 4:24
 */
public class ProducerAndConsumerWithLock {
    public static void main(String[] args) {
        ClerkWithLock clerk = new ClerkWithLock();

        ProducerWithLock producerWithLock = new ProducerWithLock(clerk);
        ConsumerWithLock consumerWithLock = new ConsumerWithLock(clerk);

        new Thread(producerWithLock, "生产者 A").start();
        new Thread(consumerWithLock, "消费者 B").start();

        new Thread(producerWithLock, "生产者 C").start();
        new Thread(consumerWithLock, "消费者 D").start();
    }
}

class ClerkWithLock {
    private volatile int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void getProduct() {
        lock.lock();
        try {
            while (product >= 1) {
                System.out.println("产品已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ++product;
            System.out.println(Thread.currentThread().getName() + " : " + product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    //出货
    public void saleProduct() {
        lock.lock();
        try {
            while (product <= 0) {
                System.out.println("缺货");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            --product;
            System.out.println(Thread.currentThread().getName() + " : " + product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class ProducerWithLock implements Runnable {

    private ClerkWithLock clerk;

    ProducerWithLock(ClerkWithLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.getProduct();
        }
    }
}

class ConsumerWithLock implements Runnable {
    private ClerkWithLock clerk;

    ConsumerWithLock(ClerkWithLock clerk) {
        this.clerk = clerk;
    }


    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.saleProduct();
        }
    }
}
