package com.learn.算法.od;

import java.util.HashMap;
import java.util.Scanner;

public class Four {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(getResult(sc.next()));
        }
    }

    private static int getResult(String str) {
        HashMap<Character,Integer> count = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            count.put(c,count.getOrDefault(c,0)+1);
        }
        int avg = str.length()/4;
        //记录多余字母个数
        int total = 0;
        boolean flag = true;
        for (Character c : count.keySet()){
            if(count.get(c)>avg){
                flag = false;
                count.put(c,count.get(c)-avg);
                total+=count.get(c);
            }else{
                count.put(c,0);
            }
        }
        if(flag){
            return 0;
        }
        int i=0,j=0;
        int minLen = str.length()+1;
        while (j<str.length()){
            Character jc = str.charAt(j);
            if(count.get(jc)>0){
                total--;
            }
            count.put(jc,count.get(jc)-1);
            while (total == 0){
                minLen = Math.min(minLen, j-i+1);
                Character ic = str.charAt(i);
                if(count.get(ic)>=0){
                    total++;
                }
                count.put(ic,count.get(ic)+1);
                i++;
            }
            j++;
        }
        return minLen;
    }
}
