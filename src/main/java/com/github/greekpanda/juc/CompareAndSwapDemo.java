package com.github.greekpanda.juc;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 4:03
 */
public class CompareAndSwapDemo {
    private static int test = 0;
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int expectValue = cas.getValue();
                boolean b = cas.compareAndSet(expectValue, (int) (Math.random() * 101));
                System.out.println("current thread " + Thread.currentThread().getName()
                        + " ,expect value: " + expectValue);
                System.out.println(b);
                test++;
                System.out.println(test);
            };
            runnable.run();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.getValue();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                    test++;
                }
            }).start();

            System.out.println(test);
        }
    }
}

class CompareAndSwap {
    private int value;

    //获取内存值
    public synchronized int getValue() {
        return this.value;
    }
//
//    //设置内存值
//    public synchronized void setValue(int v) {
//        this.value = v;
//    }


    //比较
    public synchronized int compareAndSwap(int expectValue, int newValue) {
        int oldValue = value;

        if (oldValue == expectValue) {
            this.value = newValue;
        }
        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectValue, int newValue) {
        return expectValue == compareAndSwap(expectValue, newValue);
    }
}