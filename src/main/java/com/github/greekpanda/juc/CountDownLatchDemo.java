package com.github.greekpanda.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 3:59
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(50);
        TestLatch testLatch = new TestLatch(countDownLatch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(testLatch).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

class TestLatch implements Runnable {
    private CountDownLatch countDownLatch;

    public TestLatch(CountDownLatch cdl) {
        this.countDownLatch = cdl;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 50000; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            countDownLatch.countDown();
        }
    }
}