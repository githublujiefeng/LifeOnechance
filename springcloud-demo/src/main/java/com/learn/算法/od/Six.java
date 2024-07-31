package com.learn.算法.od;
//不含101的数

import java.util.Arrays;
import java.util.Scanner;

/**
 * 小明在学习二进制时，发现了一类不含 101的数，也就是：
 * 将数字用二进制表示,不能出现101。
 * 现在给定一个整数区间[l,r] ,请问这个区间包含了多少个不含101的数?
 * 输入描述
 * 输入的唯——行包含两个正整数l, r(1slsrs10^9) 。
 * 输出描述
 * 输出的唯——行包含一个整数，表示在[l,r]区间内一共有几个不含 101的数。
 * 样例样例一：
 * 输入
 * 1 10
 * 输出
 * 8
 * 样例解释
 * 区间[1,10]内, 5的二进制表示为101, 10的二进制表示为1010,因此区间[1,10]内有10-2-8个不含101的数。
 * 样例二：
 * 输入
 * 10 20
 * 输出
 * 7
 * 样例解释
 * 区间[10,20]内,满足条件的数字有[12,14,15,16,17,18,19]因此答案为7。
 *
 */
public class Six {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int L = sc.nextInt();
        int R = sc.nextInt();
        int count = digitSearch(R) - digitSearch(L-1);
        System.out.println(count);
    }


    static int digitSearch(int num) {
        Integer[] arr = Arrays.stream(Integer.toBinaryString(num).split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        int[][][] f = new int[arr.length][2][2];
        int res = dfs(0,true,f,arr,0,0);
        return res;
    }

    private static int dfs(int p, boolean limit, int[][][] f, Integer[] arr, int pre, int prepre) {
        if(p ==arr.length)return 1;
        if(!limit && f[p][pre][prepre]!=0)
            return f[p][pre][prepre];
        int max = limit ? arr[p]:1;
        int count = 0;
        for (int i = 0; i <= max; i++) {
            if(i==1 && pre ==0 && prepre ==1)continue;
            count+=dfs(p+1,limit && i==max, f, arr, i, pre);
        }
        if(!limit) f[p][pre][prepre] =count;
        return count;
    }

}




