package com.george.jvm;

/**
 * <p>
 * JVM参数调优
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/11 23:21
 * @since JDK 1.8
 */
public class HeapTest01 {
    public static void main(String[] args) {
        // 返回java虚拟机试图使用的最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 返回java虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("MAX_MEMORY=" + maxMemory + "字节、" + maxMemory / (double) 1024 / 1024 + "MB");
        System.out.println("TOTAL_MEMORY=" + totalMemory + "字节、" + totalMemory / (double) 1024 / 1024 + "MB");

    }
}
