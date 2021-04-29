package com.learn.model_GoF_23.Factory.FactoryMethod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class AnimalFarmTest {

    public static void main(String[] args) {
        int n=100;
        HashSet m[]=new HashSet[n+1];
        m[0]= new HashSet();
        m[0].add("()");
        try {
            Animal a;
            AnimalFarm af;
            af = (AnimalFarm) ReadXML1.getObject();
            a = af.newAnimal();
            a.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

//抽象产品：动物类
interface Animal {
    public void show();
}
//具体产品：马类
class Horse implements Animal {
    JScrollPane sp;
    JFrame jf = new JFrame("工厂方法模式测试");
    public Horse() {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：马"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\FactoryMethod\\A_Horse.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭
    }
    public void show() {
        jf.setVisible(true);
    }
}
//具体产品：牛类
class Cattle implements Animal {
    JScrollPane sp;
    JFrame jf = new JFrame("工厂方法模式测试");
    public Cattle() {
        Container contentPane = jf.getContentPane();
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\FactoryMethod\\A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭
    }
    public void show() {
        jf.setVisible(true);
    }
}
//抽象工厂：畜牧场
interface AnimalFarm {
    public Animal newAnimal();
}
//具体工厂：养马场
class HorseFarm implements AnimalFarm {
    public Animal newAnimal() {
        System.out.println("新马出生！");
        return new Horse();
    }
}
//具体工厂：养牛场
class CattleFarm implements AnimalFarm {
    public Animal newAnimal() {
        System.out.println("新牛出生！");
        return new Cattle();
    }
}

