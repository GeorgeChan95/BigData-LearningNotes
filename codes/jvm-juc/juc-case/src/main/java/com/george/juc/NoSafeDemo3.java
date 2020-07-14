package com.george.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>
 * 演示不安全的集合操作
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 14:46
 * @since JDK 1.8
 */
public class NoSafeDemo3 {
    public static void main(String[] args) {
        // 多线程操作list
        listNotSafe();
        // 多线程操作set
        setNotSafe();
        // 多线程操作map
        mapNotSafe();
    }

    /**
     * 多线程操作list集合
     */
    public static void listNotSafe() {
        // new ArrayList() 线程不安全
        // new Vector<>(); 线程安全，过时
        // Collections.synchronizedList(new ArrayList<>()); 线程安全，性能较差
        // new CopyOnWriteArrayList<>(); 线程安全且性能更好
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, "线程-" + i).start();
        }
    }

    /**
     * 多线程操作set集合
     * set集合底层是 HashMap
     */
    public static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 多线程操作 HashMap
     */
    public static void mapNotSafe() {
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, "线程-" + i).start();
        }
    }
}
