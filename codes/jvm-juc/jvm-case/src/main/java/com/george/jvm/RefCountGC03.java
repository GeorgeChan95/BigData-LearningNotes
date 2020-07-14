package com.george.jvm;

/**
 * <p>
 *     演示GC的引用计数法
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/13 22:04
 * @since JDK 1.8
 */
public class RefCountGC03 {
    //这个成员属性唯一的作用就是占用一点内存
    private byte[] bigSize = new byte[2 * 1024 * 1024];
    Object instance = null;

    public static void main(String[] args) {
        RefCountGC03 objectA = new RefCountGC03();
        RefCountGC03 objectB = new RefCountGC03();
        // 循环引用
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;

        System.gc();
    }

}
