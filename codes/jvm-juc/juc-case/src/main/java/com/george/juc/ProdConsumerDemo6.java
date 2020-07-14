package com.george.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * juc生产者与消费者---下
 * <p>
 * 三板斧：
 * 判断、操作数据、通知其它线程
 * <p>
 * 需求：
 * 现在两个线程，可以操作初始值为零的一个变量，实现一个线程对该变量加1，一个线程对该变量减1，实现交替，来10轮，变量初始值为零。
 * 如果将两个线程一赠一减，更改为4个线程2增2减数据是否正常？
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 20:11
 * @since JDK 1.8
 */
public class ProdConsumerDemo6 {
    public static void main(String[] args) {
        Aircondition6 aircondition = new Aircondition6();
        // 线程A 执行数值加
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    aircondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        // 线程B，执行数值减
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    aircondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
    }
}

/**
 * 内部类
 * 定义变量和方法
 * 使用新版本多线程锁来编写
 */
class Aircondition6 {
    // 定义变量和初始值
    private int number = 0;
    // 定义可重入锁
    private Lock lock = new ReentrantLock();
    // 定义 condition
    Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        // 锁住当前方法
        lock.lock();
        try {
            // 数据判断
            while (number != 0) {
                condition.await();
            }
            // 数据操作
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 唤醒其它线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        // 锁住当前方法
        lock.lock();
        try {
            // 数据判断
            while (number == 0) {
                condition.await();
            }
            // 数据操作
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 唤醒其它线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
