package com.github.greekpanda.juc;

import org.junit.Test;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 3:41
 */
public class TestProducerAndConsumer {
    @Test
    public void test() {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "Producer A").start();
        new Thread(consumer, "Consumer B").start();

        new Thread(producer, "Producer C").start();
        new Thread(consumer, "Consumer D").start();
    }
}

class Clerk {
    private int product = 0;

    public synchronized void get() {
        while (product >= 1) {
            System.out.println("产品已满");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + product);
        this.notifyAll();
    }

    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            --product;
        }
        System.out.println(Thread.currentThread().getName() + " : " + product);
        this.notifyAll();
    }
}

class Producer implements Runnable {

    private Clerk clerk;

    Producer(Clerk clerk) {
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
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
