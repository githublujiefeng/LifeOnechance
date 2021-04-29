package com.learn.model_GoF_23.Factory.Simple_Factory_Pattern;

import java.util.ArrayList;
import java.util.List;

public class Client {

    static class Solution {
        public List<String> generateParenthesis(int n) {
            ArrayList<String> list = new ArrayList<>();
            _recur(0,0, n,"",list);
            return list;
        }

        private void _recur(int left,int right, int n, String s,ArrayList list) {
            //左括号数量大于右括号，左括号数量小于n
            if(left==n&&right==n){
                list.add(s);
                return;
            }
            if(left<n)
            _recur(left+1,right,n,s+"(",list);
            if(left>right)
            _recur(left,right+1,n,s+")",list);
        }
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.generateParenthesis(3);
    }
    //抽象产品
    public interface Product {
        void show();
    }
    //具体产品：ProductA
    static class ConcreteProduct1 implements Product {
        public void show() {
            System.out.println("具体产品1显示...");
        }
    }
    //具体产品：ProductB
    static class ConcreteProduct2 implements Product {
        public void show() {
            System.out.println("具体产品2显示...");
        }
    }
    final class Const {
        static final int PRODUCT_A = 0;
        static final int PRODUCT_B = 1;
        static final int PRODUCT_C = 2;
    }
    static class SimpleFactory {
        public static Product makeProduct(int kind) {
            switch (kind) {
                case Const.PRODUCT_A:
                    return new ConcreteProduct1();
                case Const.PRODUCT_B:
                    return new ConcreteProduct2();
            }
            return null;
        }
    }
}