package com.learn.算法.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class One {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
            Integer arr[] = Arrays.stream(sc.nextLine().split("[\\[\\]\\,\\s]"))
                    .filter(str -> !"".equals(str))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
            String num = sc.next();
            System.out.println(soulation(arr, Integer.parseInt(num)));

    }

    static String soulation(Integer[] arr,int num){
        ArrayList<Integer> link1 = new ArrayList<>();
        ArrayList<Integer> link2 = new ArrayList<>();
        Arrays.sort(arr,(a,b)->a-b);
        for (Integer i :
                arr) {
            if(i< 4) link1.add(i);
            else link2.add(i);
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int len1 = link1.size();
        int len2 = link2.size();
        switch (num){
            case 1:
                if(len1==1 || len2 ==1){
                    if(len1==1)dfs(link1,0,1,new ArrayList<>(),res);
                    if(len2==1)dfs(link2,0,1,new ArrayList<>(),res);
                }else if(len1==3||len2==3){
                    if(len1==3)dfs(link1,0,1,new ArrayList<>(),res);
                    if(len2==3)dfs(link2,0,1,new ArrayList<>(),res);
                }else if(len1==2||len2==2){
                    if(len1==2)dfs(link1,0,1,new ArrayList<>(),res);
                    if(len2==2)dfs(link2,0,1,new ArrayList<>(),res);
                }else if(len1==4||len2==4){
                    if(len1==4)dfs(link1,0,1,new ArrayList<>(),res);
                    if(len2==4)dfs(link1,0,1,new ArrayList<>(),res);
                }
                break;
            case 2:
                if(len1==2 || len2 ==2){
                    if(len1==2)dfs(link1,0,2,new ArrayList<>(),res);
                    if(len2==2)dfs(link2,0,2,new ArrayList<>(),res);
                }else if(len1==4||len2==4){
                    if(len1==4)dfs(link1,0,2,new ArrayList<>(),res);
                    if(len2==4)dfs(link2,0,2,new ArrayList<>(),res);
                }else if(len1==3||len2==3){
                    if(len1==3)dfs(link1,0,2,new ArrayList<>(),res);
                    if(len2==3)dfs(link2,0,2,new ArrayList<>(),res);
                }
                break;
            case 4:
                if(len1==4 || len2 ==4){
                    if(len1==4)res.add(link1);
                    if(len2==4)res.add(link2);
                }
                break;
            case 8:
                if(arr.length==8){
                    res.add(Stream.concat(link1.stream(),link2.stream())
                    .collect(Collectors.toCollection(ArrayList<Integer>::new)));
                }
                break;
        }
        return res.toString();
    }

    public static void dfs(ArrayList<Integer> arr, int index, int level, ArrayList<Integer> path
    ,ArrayList<ArrayList<Integer>> res){
        if(path.size()==level){
            res.add((ArrayList<Integer>) path.clone());
        }
        for (int i = index; i < arr.size(); i++) {
            path.add(arr.get(i));
            dfs(arr,i+1,level,path,res);
            path.remove(path.size()-1);
        }
    }
}
