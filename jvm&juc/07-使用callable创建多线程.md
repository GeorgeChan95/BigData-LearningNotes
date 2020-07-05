## 使用Callable创建多线程

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- jdk 1.8



### Callable与Runable

Callable 接口 与 Runnable 接口类似，不同的是它们的唯一的 run 方法：

- Callable 有返回值，Runnable 没有。

  Callable 的 call() 方法使用了 泛型类，可以返回任意类型的值。

- Callable 抛出异常 ，Runnable 没有抛出。

同时 java.util.concurrent.Executors 提供了许多方法，可以操控 Callable 在线程池中运行。





### 案例代码

`codes` --> `jvm-juc` --> `juc-case\CallableDemo7.java`

```java
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

```


