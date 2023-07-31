package com.learn.算法.od;

import java.util.ArrayList;
import java.util.List;

public class Test {
    volatile static Integer nums=0;
    static ArrayList<Integer> allData =new ArrayList<Integer>();
    static final Integer THREAD_NUMBER=33;

    public static void main(String[] args) {
        init();
        int threadNum = allData.size()/THREAD_NUMBER;
        if(allData.size()%THREAD_NUMBER>0){
            threadNum++;
        }
        ArrayList[] arrList = new ArrayList[threadNum];
        for (int i = 0; i < threadNum; i++) {
            Thread thread=new Thread(){
                @Override
                public void run() {
                    //List<Integer> list = null;
//                    if((nums+1)*THREAD_NUMBER>allData.size()){
//                        list =  allData.subList(nums*THREAD_NUMBER,allData.size());
//                    }else {
//                        list = allData.subList(nums * THREAD_NUMBER, (nums + 1) * THREAD_NUMBER);
//                    }
                    synchronized (nums) {
                        arrList[nums] = new ArrayList();
                        System.out.println(Thread.currentThread().getName());
                        doChoose(allData, arrList[nums], nums);
                        nums++;
                    }
                }
            };
            thread.start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < arrList.length; i++) {
            for (Object a :
                    arrList[i]) {
                System.out.print((Integer) a+" ");
            }
        }

    }

    public static void doChoose(List<Integer> arr, ArrayList res,int nums){
        int end=0;
        if((nums+1)*THREAD_NUMBER>allData.size()){
            end = allData.size();
        }else {
            end = (nums+1)*THREAD_NUMBER;
        }
        for (int i = nums*THREAD_NUMBER; i < end; i++) {
            if(arr.get(i)%2==1){
                res.add(arr.get(i));
            }
        }
    }

    static void init(){
        for (int i = 0; i < 67; i++) {
            allData.add(i);
        }
    }

    //f(x,y)=f(x-1,y)+f(x,y-1)
    void second(int x1,int y1){
        int[][] a = new int[6][5];
        for(int i=0;i<a.length;i++){
            for (int j=0;j<a[0].length;j++){
                if(i==0){
                    a[i][j] = 1;
                }else if(j==0){
                    a[i][j] = 1;
                }else {
                    a[i][j] = a[i - 1][j] + a[i][j - 1];
                }
            }
        }

        System.out.println(a[5][4]);
    }

    public static void main1(String[] args) {
        new Test().second(3,2);
    }
}
