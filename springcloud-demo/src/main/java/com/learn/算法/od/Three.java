package com.learn.算法.od;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 字符串重新排列、重新排序
 */
public class Three {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] readArry = line.split(" ");
        HashMap<String,Integer> mapCount = new HashMap<>();
        for (int i = 0; i < readArry.length; i++) {
            String[] wordArry = readArry[i].split("");
            String wordRes = Arrays.stream(wordArry).sorted((a, b) -> a.compareTo(b))
                    .collect(Collectors.joining());
            readArry[i] = wordRes;
            mapCount.put(readArry[i],mapCount.getOrDefault(readArry[i],0)+1);
        }
//        System.out.println(Arrays.stream(readArry).sorted((a, b) -> a.compareTo(b))
//                .collect(Collectors.joining(" ")));
        Arrays.sort(readArry,(a,b)->
            !mapCount.get(a).equals(mapCount.get(b))
                    ? mapCount.get(b)-mapCount.get(a)
                    : a.length()==b.length() ? a.compareTo(b) : a.length()-b.length()
        );
        System.out.println(String.join(" ",readArry));
    }
}
