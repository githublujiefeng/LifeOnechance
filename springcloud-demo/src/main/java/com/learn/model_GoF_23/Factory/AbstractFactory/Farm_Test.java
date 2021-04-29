package com.learn.model_GoF_23.Factory.AbstractFactory;

import javax.swing.*;
import java.awt.*;

public class Farm_Test{
    public static void main(String[] args) {
        try {
            Farm_Factory_SG f;
            Animal a;
            Plant p;
            f = (Farm_Factory_SG) ReadXML.getObject();
            a = f.getAnimal();
            p = f.getPlant();
            a.show();
            p.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

//具体农场类：GA农场工厂
class Farm_Factory_GA implements Farm_Interface{

    @Override
    public Animal getAnimal() {
        System.out.println("新马出生！");
        return new Horse();
    }

    @Override
    public Plant getPlant() {
        System.out.println("水果长成！");
        return new Fruitage();
    }
}

//具体农产类:SG农场工厂
class Farm_Factory_SG implements Farm_Interface{
    @Override
    public Animal getAnimal() {
        System.out.println("新牛出生!");
        return new Cattle();
    }

    @Override
    public Plant getPlant() {
        System.out.println("蔬菜长成！");
        return new Vegetables();
    }
}
//具体产品：马类
class Horse implements Animal{

    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public  Horse(){
        Container contentPane= jf.getContentPane();
        JPanel p1= new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：马"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\AbstractFactory\\A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void show() {
        jf.setVisible(true);
    }
}

//具体产品：牛类
class Cattle implements Animal{
    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public  Cattle(){
        Container contentPane= jf.getContentPane();
        JPanel p1= new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\AbstractFactory\\A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void show() {
        jf.setVisible(true);
    }
}

//具体产品：水果类
class Fruitage implements Plant{

    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public  Fruitage(){
        Container contentPane= jf.getContentPane();
        JPanel p1= new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("植物：水果"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\AbstractFactory\\A_Cattle.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void show() {
        jf.setVisible(true);
    }
}

//具体产品：蔬菜类
class  Vegetables implements Plant{

    JScrollPane sp;
    JFrame jf = new JFrame("抽象工厂模式测试");

    public  Vegetables(){
        Container contentPane= jf.getContentPane();
        JPanel p1= new JPanel();
        p1.setLayout(new GridLayout(1,1));
        p1.setBorder(BorderFactory.createTitledBorder("植物：蔬菜"));
        sp = new JScrollPane(p1);
        contentPane.add(sp, BorderLayout.CENTER);
        JLabel l1 = new JLabel(new ImageIcon("C:\\Program Files\\JetBrains\\ideaProject\\springcloud-demo\\src\\main\\java\\com\\learn\\model_GoF_23\\Factory\\AbstractFactory\\P_Vegetables.jpg"));
        p1.add(l1);
        jf.pack();
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void show() {
        jf.setVisible(true);
    }
}