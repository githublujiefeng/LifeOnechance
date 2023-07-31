package com.learn.算法;

import java.util.Arrays;

public class new_Test {
    //数组 +- 乱的
    public static void main(String[] args) {
        char a[] = new char[]{'+','-','-','+','-'};
        new new_Test().test(a);
        System.out.println(Arrays.toString(a));
    }
    void test(char a[]){
        int l = 0; int r = a.length-1;
        while(l<r){
            if(a[l]=='-' && a[r]=='+'){
                swap(l,r,a);
                r--;
                l++;
            }else if(a[l]=='-'){
                r--;
            }else if(a[r]=='+'){
                l++;
            }else{
                l++;r--;
            }

        }
    }
    void swap(int l,int r,char a[]){
        char temp = a[l];
        a[l] = a[r];
        a[r] = temp;
    }
}
