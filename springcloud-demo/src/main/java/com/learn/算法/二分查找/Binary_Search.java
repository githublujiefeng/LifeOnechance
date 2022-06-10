package com.learn.算法.二分查找;


public class Binary_Search {
    boolean check(int x){
        return false;
    }

    /**
     * 二分查找模板一
     * 可以找到最右边的匹配值
     * @param left
     * @param right
     * @return
     */
    int search_Temp1(int left, int right){
        while(left < right){
            int mid = (left + right) >> 1;
            if(check(mid)){
                right = mid;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 二分查找模板二
     * 可以找到最左边的匹配值
     * @param left
     * @param right
     * @return
     */
    int search_Temp2(int left,int right){
        while (left<right){
            int mid = (left+right+1) >> 1;
            if(check(mid)){
                left = mid;
            }else {
                right = mid -1;
            }
        }
        return left;
    }
}
