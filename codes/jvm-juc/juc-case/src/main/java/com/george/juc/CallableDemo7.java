package com.george.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 22:26
 * @since JDK 1.8
 */
public class CallableDemo7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // new futureTask对象
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        // 启动线程
        new Thread(futureTask, "A").start();
        // 获取线程返回值
        Integer get = futureTask.get();
        System.out.println("Callable返回值\t" + get);
    }
}

/**
 * 内部类实现了 Callable接口，并返回了Interger类型的值
 */
class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("come int call method()");
        return 1024;
    }
}
