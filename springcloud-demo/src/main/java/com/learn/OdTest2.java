package com.learn;

import java.util.*;

public class OdTest2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[] fields =new int[m];
        int sum =0;
        for(int i=0;i<m;i++){
            fields[i] = sc.nextInt();
            sum+=fields[i];
        }
        if(m>n){
            System.out.println("-1");
        }else {
            System.out.println(doExpirDay(fields,m, n,sum));
        }
    }

    private static int doExpirDay(int[] fields,int m, int n,int sum) {
        int minNum = (int) Math.ceil((double)sum/n);
        Arrays.sort(fields);
        int index =0;
        for(int i=0;i<fields.length;i++){
            if(minNum>=fields[i]&&minNum<fields[i+1]){
                index = i;
            }
        }
//        int minNum = (int) Math.ceil((double)sum/n);
//        while(true){
//            int need = 0;
//            for (int field :
//                    fields) {
//                need += Math.ceil((double)field/minNum);
//            }
//            if(need<=n){
//                return minNum;
//            }else{
//                minNum++;
//            }
        while(true){
            int del = n-index;
            int need =0;
            for (int i=index+1 ;i<fields.length;i++) {
                need += Math.ceil((double)fields[i]/minNum);
            }
            if(del<index){

            }
        }
    }
}
