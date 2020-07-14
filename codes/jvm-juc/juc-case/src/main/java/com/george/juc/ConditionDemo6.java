package com.george.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Condition使用案例
 * <p>
 * 需求：
 * 备注：多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * 来10轮
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 21:31
 * @since JDK 1.8
 */
public class ConditionDemo6 {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print5(5, "A");
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print10(10, "B");
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.print15(15, "C");
            }
        }, "C").start();
    }
}

/**
 * 内部类
 */
class ShareData {
    // 1-A  2-B   3-C
    private int number = 1;
    private Lock lock = new ReentrantLock();
    // 定义c1/c2/c3,用于精确唤醒A/B/C三个线程
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    /**
     * 自定义打印方法
     *
     * @param time
     */
    public void print5(int time, String name) {
        lock.lock();
        try {
            // 数据判断
            while (number != 1) {
                c1.await();
            }
            // 数据操作
            for (int i = 0; i < time; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + name);
            }
            number = 2;
            // 线程唤醒
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(int time, String name) {
        lock.lock();
        try {
            // 数据判断
            while (number != 2) {
                c2.await();
            }
            // 数据操作
            for (int i = 0; i < time; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + name);
            }
            number = 3;
            // 线程唤醒
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(int time, String name) {
        lock.lock();
        try {
            // 数据判断
            while (number != 3) {
                c3.await();
            }
            // 数据操作
            for (int i = 0; i < time; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + name);
            }
            number = 1;
            // 线程唤醒
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
