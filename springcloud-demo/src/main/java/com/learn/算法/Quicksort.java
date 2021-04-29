package com.learn.算法;

//快排
public class Quicksort {

    /**
     *
     *递推公式：
     *quick_sort(p…r) = quick_sort(p…q-1) + quick_sort(q+1… r)
     *
     *终止条件：
     *p >= r
     */

    /*
    快速排序，A是数组，n表示数组的大小
    quick_sort(A, n) {
        quick_sort_c(A, 0, n-1)
    }
    // 快速排序递归函数，p,r为下标
    quick_sort_c(A, p, r) {
        if p >= r then return

                q = partition(A, p, r) // 获取分区点
        quick_sort_c(A, p, q-1)
        quick_sort_c(A, q+1, r)
    }
    */
    void quick_sort(int A[],int n){
        quick_sort_c(A,0,n-1);
    }
    void quick_sort_c(int A[],int p, int r){
        if(p>=r) return ;
        int q=partition(A, p, r); // 获取分区点
        quick_sort_c(A, p, q-1);
        quick_sort_c(A, q+1, r);
    }

    int partition(int[] A, int p, int r) {
        int piovt = A[r];
        int i = p;
        for (int j = p; j < r - 1; j++) {
            if (A[j] < piovt) {
                int temp = A[j];
                A[j] = A[i];
                A[i] = temp;
                i++;
            }
        }
        int temp = A[r];
        A[r] = A[i];
        A[i] = temp;
        return i;
    }
}
