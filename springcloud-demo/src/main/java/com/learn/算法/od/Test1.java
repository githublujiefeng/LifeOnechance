package com.learn.算法.od;

public class Test1 {
    int i;
    public Test1(){
        i=100;
        cat();
    }

    public void cat(){
        System.out.println("cat1:"+i);
    }
}
class Test2 extends Test1{
    int i;
    public Test2(){
        System.out.println("2");
    }

    @Override
    public void cat() {
        System.out.println("cat2:"+i);
    }

    public static void main(String[] args) {
        Test1 t = new Test2();
        t.cat();
    }
}