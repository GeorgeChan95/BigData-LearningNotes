package com.george.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *     售票 demo
 *
 *     1、声明内部类
 *     2、主类使用多线程调用内部类方法
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 10:38
 * @since JDK 1.8
 */
public class SaleTicketDemo1 {
    // 主线程，一切程序的入口
    public static void main(String[] args) {
        // 创建资源类（Ticket） 对象
        Ticket ticket = new Ticket();
        // 使用 Lambda表达式创建多线程程序更简洁
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "B").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();}, "C").start();

        // 传统的创建多线程的方式
        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=40; i++) ticket.sale();
                {
                    ticket.sale();
                }
            }
        }, "A").start();*/
    }
}

class Ticket {
    private final Logger LOGGER = LoggerFactory.getLogger(Ticket.class);
    // 定义资源数量
    private int number = 30;
    // 声明可重入锁
    Lock lock = new ReentrantLock();

    /**
     * 定义售票方法，使用 Lock
     */
    public void sale() {
        // 先锁住这个售票方法，当方法完成一次售票动作后在解锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第：" + (number--) + "\t 还剩下： " + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 解锁
            lock.unlock();
        }
    }
}
