package com.learn.model_GoF_23.Prototype;

//原型模式的测试类
public class PrototypeTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Realizetype obj1 = new Realizetype();
        Realizetype obj2 = (Realizetype) obj1.clone();
        System.out.println("obj1==obj2?" + (obj1 == obj2));
        System.out.println("obj1.hello==obj2.hello?" + (obj1.hello == obj2.hello));
    }
}