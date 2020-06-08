package com.github.greekpanda.juc;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 2:15
 */
public class TestCopyOnWriteArrayList {
    @Test
    public void test() throws InterruptedException {
        TestThread testThread = new TestThread();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            new Thread(testThread).start();
        }
    }
}

class TestThread implements Runnable {
    private static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    static {
        copyOnWriteArrayList.add("aaa");
        copyOnWriteArrayList.add("bbb");
        copyOnWriteArrayList.add("ccc");
        copyOnWriteArrayList.add("ddd");
    }

    @Override
    public void run() {
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + "\t");
            copyOnWriteArrayList.add("aaa");
//            copyOnWriteArrayList.add("fff");
        }
    }
}
