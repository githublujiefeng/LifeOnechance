package com.learn.算法;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class leetCode946 {
    Deque<Integer> queue = new ArrayDeque<>();
    void test(){

    }
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int length = pushed.length;
        for(int i=0,j=0;i<length;i++){
            stack.push(pushed[i]);
            while(!stack.isEmpty()&&stack.peek()==popped[j]){
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }
    public int[] finalPrices(int[] prices) {
        int length = prices.length;
        int[] res = Arrays.copyOf(prices,length);
        for(int i=0;i<length;i++){
            for(int j=i+1;j<length;j++){
                if(prices[j]<=prices[i]){
                    res[i]=prices[i]-prices[j];
                    break;
                }
            }
        }
        return res;
    }
}
