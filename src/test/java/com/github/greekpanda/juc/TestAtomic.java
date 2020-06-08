package com.github.greekpanda.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * * 		  int i = 10;
 * * 		  i = i++; //10
 * *
 * * 		  int temp = i;
 * * 		  i = i + 1;
 * * 		  i = temp;
 * *
 * * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * * 		1. volatile 保证内存可见性
 * * 		2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * * 			CAS 算法是硬件对于并发操作的支持
 * * 			CAS 包含了三个操作数：
 * * 			①内存值  V
 * * 			②预估值  A
 * * 			③更新值  B
 * * 			当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 1:40
 */
public class TestAtomic {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);


    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; ++i) {
            Thread.sleep(1000);
            new Thread(() -> atomicInteger.incrementAndGet()).start();
            System.out.println(atomicInteger.get());
        }

        System.out.println("-----------------------");

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            Runnable runnable = () -> atomicInteger.incrementAndGet();
            runnable.run();
            System.out.println(atomicInteger.get());
        }

    }
}
