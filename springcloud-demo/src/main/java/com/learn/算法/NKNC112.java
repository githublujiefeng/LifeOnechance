package com.learn.算法;

public class NKNC112 {

    /**
     * 描述
     * 给定一个十进制数 M ，以及需要转换的进制数 N 。将十进制数 M 转化为 N 进制数。
     *
     * 当 N 大于 10 以后， 应在结果中使用大写字母表示大于 10 的一位，如 'A' 表示此位为 10 ， 'B' 表示此位为 11 。
     *
     * 若 M 为负数，应在结果中保留负号。
     */
    /**
     * 进制转换
     * @param M int整型 给定整数
     * @param N int整型 转换到的进制
     * @return string字符串
     */
    public String solve (int M, int N) {
        // write code here

        int i=M;
        String k="";
        if(i<0){
            i=-M;
            k="-";
        }
        int m=0;
        StringBuilder builder = new StringBuilder();
        while(i>0){
            m=i%N;
            i=i/N;
            if(m>=10){
                builder.insert(0,(char) ('A'+m-10));
            }else {
                builder.insert(0, m);
            }
        }
        return  k+builder.toString();
    }

    public static void main(String[] args) {
       // System.out.println(new NKNC112().solve(-26,16));
        System.out.println(new NKNC112().doTest());
    }
    int doTest(){
        int m=1;
        try{
            m=5;
            return m;
        }catch (Exception e){
            return 2;
        }finally {
            m++;
            System.out.println(m);
        }
    }
}
