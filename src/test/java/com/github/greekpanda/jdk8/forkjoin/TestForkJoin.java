package com.github.greekpanda.jdk8.forkjoin;

import com.github.greekpanda.jdk8.date.ForkJoinCalculate;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 上午 9:19
 */
public class TestForkJoin {
    @Test
    public void test0() {
        long start = System.currentTimeMillis();

        long sum = 0L;
        for (long i = 0; i < 10000000000L; ++i) {
            sum += i;
        }

        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费时间： " + (end - start)); // 468ms
    }


    @Test
    public void test1() {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);

        long sum = forkJoinPool.invoke(task);
        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费时间： " + (end - start)); // 耗费时间： 1575
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(0L, 10000000000L)
                .parallel()
                .sum();
        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费时间： " + (end - start)); // 耗费时间：1264

    }
}
