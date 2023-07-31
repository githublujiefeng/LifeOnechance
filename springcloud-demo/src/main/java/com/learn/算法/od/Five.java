package com.learn.算法.od;

import java.util.Arrays;
import java.util.Scanner;

public class Five {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer[] array = Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        int timeSize = sc.nextInt();
        int[] count = new int[3];
        int l = 0;
        int r = l+timeSize;
        int max = 0;
        for (int i = l; i < r; i++) {
            count[array[i]]++;
        }
        max = Math.max(count[0],Math.max(count[1],count[2]));
        while(r<array.length){
            count[array[l++]]--;
            count[array[r++]]++;
            max = Math.max(count[0],Math.max(count[1],count[2]));
        }
        System.out.println(max);
    }
}
