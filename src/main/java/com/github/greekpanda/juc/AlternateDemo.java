package com.github.greekpanda.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 4:50
 */
public class AlternateDemo {
    public static void main(String[] args) {
        Alternate alternate = new Alternate();
        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                alternate.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                alternate.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                alternate.loopC(i);
            }
        }, "C").start();
    }
}

class Alternate {

    //当前正在执行线程的标记
    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * @param totalLoop : 循环第几轮
     */
    public void loopA(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (number != 1) {
                condition1.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (number != 2) {
                condition2.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (number != 3) {
                condition3.await();
            }

            //2. 打印
            for (int i = 1; i <= 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}


//class Alternate {
//    private int num = 1;
//
//    private Lock lock = new ReentrantLock();
//
//    private Condition condition1 = lock.newCondition();
//    private Condition condition2 = lock.newCondition();
//    private Condition condition3 = lock.newCondition();
//
//    public void loopA(int totalLoop) {
//        lock.lock();
//        try {
//            //1. 如果是1，等待
//            if (num == 1) {
//                condition1.await();
//            }
//
//            System.out.println("1111111111111111");
//            //2. 逐个输出
//            for (int i = 1; i <= 1; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
//            }
//
//            //3. 唤醒
//            num = 2;
//            condition2.signal();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//
//    }
//
//    public void loopB(int totalLoop) {
//        lock.lock();
//
//        try {
//            //1. 判断
//            if (num != 2) {
//                condition2.await();
//            }
//
//            //2. 打印
//            for (int i = 1; i <= 1; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
//            }
//
//            //3. 唤醒
//            num = 3;
//            condition3.signal();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void loopC(int totalLoop) {
//        lock.lock();
//
//        try {
//            //1. 判断
//            if (num != 3) {
//                condition3.await();
//            }
//
//            //2. 打印
//            for (int i = 1; i <= 1; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
//            }
//
//            //3. 唤醒
//            num = 1;
//            condition1.signal();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//}
