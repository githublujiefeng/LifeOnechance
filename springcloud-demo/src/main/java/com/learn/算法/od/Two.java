package com.learn.算法.od;

import java.util.HashMap;
import java.util.Scanner;

public class Two {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String firstLine = sc.nextLine();
        String first = firstLine.split(" ")[0];
        int num = Integer.parseInt(firstLine.split(" ")[1]);
        HashMap<String,String> map = new HashMap<>();
        int i= num;
        while(i>0){
            String line = sc.nextLine();
            String[] readArr = line.split(" ");
            if(!map.containsKey(readArr[0])){
                map.put(readArr[0],readArr[1]+" "+readArr[2]);
            }
            i--;
        }
        int res[] = new int[num];
        while(map.size()>0){
            String mapValue = map.get(first);
            map.remove(first);
            String[] value = mapValue.split(" ");
            res[i++] = Integer.parseInt(value[0]);
            first = value[1];
        }
        System.out.println(res[res.length/2]);
    }
}
