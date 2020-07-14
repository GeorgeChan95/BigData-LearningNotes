package com.george.juc;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * synchronized 使用
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 16:41
 * @since JDK 1.8
 */
public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone2.sendSMS();
        }).start();


//        new Thread(() -> {
//            phone.sayHello();
//        }).start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}


