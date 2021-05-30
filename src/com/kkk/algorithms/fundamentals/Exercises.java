package com.kkk.algorithms.fundamentals;

import com.kkk.supports.Node;
import com.kkk.supports.Queue;
import com.kkk.supports.Stack;
import com.kkk.supports.Utils;
import java.util.Arrays;
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
        // 等于情况下，向左遍历直到第一个不等于（小于）key的元素
        // 其余情况下等同于二分查找
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
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
        // 根据算法原理可知low就是小于key的个数
        return lo;
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
        int key = 20;
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(30);
        }
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int rank = rank(key, arr);
        int count = count(key, arr);
        System.out.println(rank);
        System.out.println(count);
        // 打印出等于key的元素
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, rank, rank + count)));
    }

    /**
     * 1.2.6
     * 回环变位 字符串s循环移动任意位置后得到t 判断两个字符串是否互为回环变位
     */
    // 仅使用 length() indexOf() 方法
    public static boolean circularRotation(String s, String t) {
        // 拼接两个t必然包含s
        return s.length() == t.length() && (t + t).indexOf(s) >= 0;
    }

    /**
     * 1.3.3
     * 将 0-9 顺序入栈，判断出栈序列是否可能
     */
    public static boolean isPopSequence(int[] arr) {
        Stack stack = new Stack();
        int i = 0;
        for (int n : arr) {
            // 如果栈为空 或 当前出栈不匹配 则入栈
            while (stack.isEmpty() || stack.peek() != n) {
                // 入栈完毕还是不匹配则 return
                if (i > 9) {
                    return false;
                }
                stack.push(i++);
            }
            // 匹配则出栈
            stack.pop();
        }
        return true;
    }

    // 1.3.3
    public static void isPopSequenceTest() {
        System.out.println(isPopSequence(new int[]{4, 3, 2, 1, 0, 9, 8, 7, 6, 5}));
        System.out.println(isPopSequence(new int[]{4, 6, 8, 7, 5, 3, 2, 9, 0, 1}));
        System.out.println(isPopSequence(new int[]{2, 5, 6, 7, 4, 8, 9, 3, 1, 0}));
        System.out.println(isPopSequence(new int[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9}));
        System.out.println(isPopSequence(new int[]{1, 2, 3, 4, 5, 6, 9, 8, 7, 0}));
        System.out.println(isPopSequence(new int[]{0, 4, 6, 5, 3, 8, 1, 7, 2, 9}));
        System.out.println(isPopSequence(new int[]{1, 4, 7, 9, 8, 6, 5, 3, 0, 2}));
        System.out.println(isPopSequence(new int[]{2, 1, 4, 3, 6, 5, 8, 7, 9, 0}));
    }

    /**
     * 1.3.30
     * 反转链表
     */
    // 迭代方法
    public static Node reverse1(Node t) {
        // 指向剩余未反转链表的首结点
        Node first = t;
        // 指向反转链表的首结点
        Node reverse = null;
        // 循环终止条件 所有链表都反转完
        while (first != null) {
            // 指向剩余结点的第二个结点
            Node second = first.next;
            // 将当前首结点移到反转链表上
            first.next = reverse;
            // 反转链表首结点变成当前首节点
            reverse = first;
            // 未反转链表首结点后移
            first = second;
        }
        return reverse;
    }

    // 递归方法
    public static Node reverse2(Node first) {
        if (first == null || first.next == null) {
            return first;
        }
        // 记录下一个结点，将剩余的链表反转后，该结点就是最后一个结点
        Node second = first.next;
        // 反转剩余链表得到首结点
        Node rest = reverse2(second);
        // 将当前first移到反转链表最后面
        second.next = first;
        first.next = null;
        return rest;
    }

    //1.3.30
    public static void reverseTest() {
        Node node = new Node(1);
        Node first = node;
        for (int i = 2; i <= 10; i++) {
            Node temp = new Node(i);
            node.next = temp;
            node = temp;
        }
        first = reverse1(first);
        Utils.printNode(first);
        first = reverse2(first);
        Utils.printNode(first);
        first = reverse2(first);
        Utils.printNode(first);
        first = reverse1(first);
        Utils.printNode(first);
    }

    /**
     * 1.3.37
     * 约瑟夫问题 使用队列 （41个人到3则出列，最后剩16、31）
     */
    public static int[] josephus(int n, int m) {
        int[] arr = new int[n];
        Queue queue = new Queue();
        // 从 1 开始
        for (int i = 1; i <= n; i++) {
            queue.enqueue(i);
        }
        for (int count = 0, i = 0; i < n; ) {
            int out = queue.dequeue();
            count++;
            if (count == m) {
                count = 0;
                arr[i++] = out;
            } else {
                queue.enqueue(out);
            }
        }
        return arr;
    }

    // 1.3.37
    public static void josephusTest() {
        System.out.println(Arrays.toString(josephus(7, 2)));
        System.out.println(Arrays.toString(josephus(41, 3)));
    }

    /**
     * 1.4.15
     * 平方级别算法实现 3-sum
     */
    public static int count3Sum(int[] arr) {
        Arrays.sort(arr);
        int count = 0;
        for (int i = 0; i < arr.length - 2; i++) {
            int lo = i + 1;
            int hi = arr.length - 1;
            while (lo < hi) {
                int sum = arr[i] + arr[lo] + arr[hi];
                if (sum > 0) {
                    hi--;
                } else if (sum < 0) {
                    lo++;
                } else {
                    System.out.println(arr[i] + "+" + arr[lo] + "+" + arr[hi]);
                    count++;
                    lo++;
                    hi--;
                }
            }
        }
        return count;
    }

    /**
     * 1.4.18
     * 数组的局部最小值
     * 使用二分法，一直在较小相邻元素的半边查找即可
     * 将数组看作函数图像求极小值试试，就可以明白该方法是正确的。
     */
    /**
     * 1.4.20
     * 双调查找，一个双调数组是先递增再递减
     * 所以先用1.4.18的方法先找到极大值，然后在左右区间分别二分查找
     */

}
