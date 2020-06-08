package com.github.greekpanda.juc;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 2:27
 */
public class TestCountLatch {
    @Test
    public void test() {
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
