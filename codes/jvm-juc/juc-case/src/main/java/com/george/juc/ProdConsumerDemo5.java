package com.george.juc;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * juc生产者与消费者---上
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
public class ProdConsumerDemo5 {
    public static void main(String[] args) {
        Aircondition5 aircondition = new Aircondition5();
        // 线程A 执行数值加
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(200);
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
                    TimeUnit.MICROSECONDS.sleep(200);
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
 */
class Aircondition5 {
    // 定义变量和初始值
    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        // 数据判断（多线程不能用if做判断，会造成虚假唤醒）
        while (number != 0) {
            this.wait();
        }

        // 数据操作
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);

        // 唤醒其它线程
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        // 数据判断
        while (number == 0) {
            this.wait();
        }
        // 数据操作
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);

        // 唤醒其它线程
        this.notifyAll();
    }
}
