package com.george.jvm;

import java.util.Random;

/**
 * <p>
 *     演示堆内存溢出
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/11 23:48
 * @since JDK 1.8
 */
public class HeapOomTest02 {
    public static void main(String[] args) {
        String str = "hello, I am Heap!";
        while (true) {
            str += str + new Random().nextInt(8888888) + new Random().nextInt(9999999);
        }
    }
}
