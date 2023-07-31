package com.learn;

public class TestTwo {
    public void one(int[][] a){
        long startTime = System.currentTimeMillis();
        int sum=0;
        for (int i = 0; i < 10001; i++) {
            for (int j = 0; j < 10001; j++) {
                sum+=a[i][j];
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("first: "+(end-startTime));
    }

    public void two(int[][] a){
        long startTime = System.currentTimeMillis();
        int sum=0;
        for (int i = 0; i < 10001; i++) {
            for (int j = 0; j < 10001; j++) {
                sum+=a[j][i];
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("second:"+(end-startTime));
    }

    public static void main(String[] args) {
        TestTwo testTwo = new TestTwo();
        int[][] a = new int[10002][10002];
        testTwo.one(a);
        testTwo.two(a);
    }
}
