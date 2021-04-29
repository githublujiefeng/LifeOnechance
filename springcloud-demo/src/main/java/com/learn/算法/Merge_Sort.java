package com.learn.算法;

import java.util.Arrays;

//归并排序
public class Merge_Sort {

    /**
     * 递推公式：merge_sort(p…r) = merge(merge_sort(p…q), merge_sort(q+1…r))
     * merge_sort(p…r) 表示，给下标从 p 到 r 之间的数组排序。
     * 我们将这个排序问题转化为了两个子问题，merge_sort(p…q) 和 merge_sort(q+1…r)，其中下标 q 等于 p 和 r 的中间位置，也就是 (p+r)/2。
     * 当下标从 p 到 q 和从 q+1 到 r 这两个子数组都排好序之后，我们再将两个有序的子数组合并在一起，这样下标从 p 到 r 之间的数据就也排好序了。
     */


// 归并排序算法, A是数组，n表示数组大小
    void merge_sort(int []A, int n) {
        merge_sort_c(A, 0, n-1);
    }

    // 递归调用函数
    void merge_sort_c(int []A, int p, int r) {
        // 递归终止条件
        if (p >= r)
            return;
        // 取p到r之间的中间位置q
        int q = (p+r) / 2;
        // 分治递归
        merge_sort_c(A, p, q);
        merge_sort_c(A, q+1, r);
        // 将A[p...q]和A[q+1...r]合并为A[p...r] 一个有序的数组
        merge(A, p, r);
    }
    //合并函数
    void merge(int A[],int p,int r){
        int q= (p+r)/2;
        int arr[]=new int [r-p+1];
        int left=p,right=q+1,k=0;
        while (left<=right&&right<=r){
            if(A[left]<A[right]){
                arr[k++]=A[left++];
            }else{
                arr[k++]=A[right++];
            }
        }
        //判断哪个子数组中有剩余的数据
        int start=left,end=q;
        if(right<=r) {
            start = right;
            end = r;
        }
        //将剩余的数据拷贝到临时数组中
        while(start<=end){
            arr[k++]=A[start++];
        }
        //将临时数组中的值拷贝回原数组中
        for (int i=0;i<r-p;i++){
            A[p+i]=arr[i];
        }
    }


    //优化合并函数，使用哨兵
//    void merge_new(int A[], int p,int r){
//        int q=(p+r)/2;
//        int first[] = new int [q-p+1];
//        int second[] = new int [r-p+1];
//        int a[] = new int [r-p+1],k=0;
//        for(int i=0;i<q-p;i++){
//            first[i]=A[p+i];
//        }
//        first[q-p] = Integer.MAX_VALUE;
//        for(int i=0;i<q-p;i++){
//            second[i+q]=A[q+i];
//        }
//        second[r-p] = Integer.MAX_VALUE;
//        while(k)
//    }
}
