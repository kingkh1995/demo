package com.kkk.algorithms.fundamentals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author KaiKoo
 */
public class Exercises {

    /**
     * 1.1.9
     * 将一个整数（可正可负，正数省去前面的0）用二进制表示并转换为字符串
     * Integer.toBinaryString(int i)
     */
    public static String toBinaryString(int i) {
        // 特殊情况 0
        if (i == 0) {
            return "0";
        }
        // 使用 char 数组保存二进制表示
        char[] chars = new char[32];
        int index = 32;
        // 终止条件 i==0 即不再存在任何一个 1
        while (i != 0) {
            // 从最低开始使用 & 运算
            chars[--index] = (char) ('0' + (i & 1));
            // 无符号左移一位 去除掉低位
            i = i >>> 1;
        }
        // String(char value[], int offset, int count) 包含offset
        return new String(chars, index, 32 - index);
    }

    // 1.1.9
    public static void toBinaryStringTest() {
        int i = ThreadLocalRandom.current().nextInt();
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Exercises.toBinaryString(i));
        System.out.println(Integer.toBinaryString(-i));
        System.out.println(Exercises.toBinaryString(-i));
    }

    /**
     * 1.1.27
     * 求二项分布
     */
    public static double binomial(int N, int k, double p) {
        if (N == 0 && k == 0) {
            return 1.0;
        }
        if (N < 0 || k < 0) {
            return 0.0;
        }
        // 递归次数太多，有严重的效率问题
        return (1.0 - p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
    }

    // 循环求解，空间换时间
    public static double betterBinomial(int n, int k, double p) {
        // 使用二维数组保存之前的值
        double[][] matrix = new double[n + 1][k + 1];
        matrix[0][0] = 1.0D;
        // 先求出特殊情况 n=0 和 k=0
        for (int i = 1; i <= k; i++) {
            matrix[0][i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            matrix[i][0] = (1.0 - p) * matrix[i - 1][0];
        }
        // 循环求解
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                matrix[i][j] = (1.0 - p) * matrix[i - 1][j] + p * matrix[i - 1][j - 1];
            }
        }
        return matrix[n][k];
    }


    // 1.1.27
    public static void binomialTest() {
        System.out.println(binomial(10, 5, 0.25D));
        System.out.println(betterBinomial(10, 5, 0.25D));
        // 递归次数太多无法求解
//        System.out.println(binomial(100, 50, 0.25D));
        System.out.println(betterBinomial(100, 50, 0.25D));
    }

    /**
     * 1.1.29
     * 基于二分查找，对一个整型有序数组（可能存在重复值），添加一个rank()方法返回小于key值的元素个数，一个count()方法返回等于key值的元素个数。
     */
    public static int rank(int key, int[] a) {
        int lo = 0, hi = a.length - 1;
        // 终止条件 lo = hi
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // 等于情况下，向左遍历直到第一个不等于（小于）key的元素
            if (a[mid] == key) {
                while (--mid >= 0 & a[mid] == key) {
                }
                return ++mid;
            } else if (a[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        // 处理key值不存在的情况
        return a[lo] < key ? lo + 1 : lo;
    }

    public static int count(int key, int[] a) {
        int num = 0;
        for (int i = rank(key, a); i < a.length && a[i++] == key; num++) {
            ;
        }
        return num;
    }

    // 1.1.29
    public static void rankTest() {
        int n = 50;
        int key = 22;
        List<Integer> ints = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ints.add(ThreadLocalRandom.current().nextInt(30));
        }
        int[] arr = new int[n];
        Collections.sort(ints);
        for (int i = 0; i < n; i++) {
            arr[i] = ints.get(i);
            System.out.print(arr[i] + "-");
        }
        System.out.println();
        int rank = rank(key, arr);
        int count = count(key, arr);
        System.out.println(rank);
        System.out.println(count);
        // 打印出等于key的元素
        for (int i = rank; i < arr.length && i < rank + count; i++) {
            arr[i] = ints.get(i);
            System.out.print(arr[i] + "-");
        }
    }

}
